package net.medrag.devBuilder.service;

import net.medrag.devBuilder.model.Request;
import net.medrag.devBuilder.model.StartUpData;
import net.medrag.devBuilder.service.jms.JmsSender;
import net.medrag.schema.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

/**
 * {@author} Stanislav Tretyakov
 * 10.10.2019
 */
@Service
public class Processor {

    private static Map<String, Request> RESPONSES = new LinkedHashMap<>();

    @Value("${devBuilder.use.jms}")
    private Boolean useJms;

    private JmsSender jmsSender;

    @Autowired
    public Processor(JmsSender jmsSender) {
        this.jmsSender = jmsSender;
    }

    public String process(String taskid) {

        Developer developer = new Developer();
        developer.setId(taskid);

        taskid = taskid.length() > 1 ? taskid.substring(taskid.length() - 2) : "00";
        Request request = RESPONSES.get(taskid);
        request = request == null ? new Request() : request;

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy H[H]:mm");
        developer.setBirthday(request.getBday() == null || request.getBday().trim().isEmpty()
                ? LocalDateTime.now() : LocalDateTime.from(dtf.parse(request.getBday())));

        int amount = request.getSkillsCount() < 1 ? new Random().nextInt(10) + 1 : request.getSkillsCount();

        developer.setName(request.getName() == null ? "Anon" : request.getName());
        developer.setSurname(request.getSurname() == null ? "Random" : request.getSurname());
        developer.setRace(getRaceType(request.getRace()));
        developer.setSkills(new SkillList());
        developer.setAge(request.getAge() == 0
                ? new BigInteger(String.valueOf(new Random().nextInt(100) + 1))
                : new BigInteger(String.valueOf(request.getAge())));

        for (int i = 0; i < amount; i++) {
            Skill skill = new Skill();
            skill.setName(SkillName.values()[(int) (Math.random() * SkillName.values().length)]);   // Math.random() * (max - min) + min
            skill.setLevel(SkillLevel.values()[(int) (Math.random() * SkillLevel.values().length)]);
            developer.getSkills().getSkill().add(skill);
        }

        if (useJms) {
            jmsSender.sendJms(developer);
        }

        return compileXml(developer);
    }

    private String compileXml(Developer Developer) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Developer.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            StringWriter sw = new StringWriter();
            jaxbMarshaller.marshal(Developer, sw);
            return sw.toString();
        } catch (JAXBException j) {
            j.printStackTrace();
            return j.getMessage();
        }
    }

    private String getRandomNumber(int length) {
        Random r = new Random(31);
        String result = "";
        do {
            result = result.concat(String.valueOf(r.nextInt(999999999)));
        } while (result.length() < length);
        if (result.length() > length) result = result.substring(0, length);
        return result;
    }

    public Request setServicesAmount(Request request) {
        RESPONSES.put(request.getKey(), request);
        return request;
    }

    private RaceType getRaceType(String val) {
        try {
            return RaceType.valueOf(val);
        } catch (IllegalArgumentException | NullPointerException e) {
            return RaceType.values()[new Random().nextInt(RaceType.values().length)];
        }
    }

    public StartUpData getStartUpData() {
        return StartUpData.builder().raceTypes(RaceType.values()).requests(RESPONSES).status(200).build();
    }
}
