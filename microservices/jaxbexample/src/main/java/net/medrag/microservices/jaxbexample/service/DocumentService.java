package net.medrag.microservices.jaxbexample.service;

import net.medrag.microservices.jaxbexample.xsd.ObjectFactory;
import net.medrag.microservices.jaxbexample.xsd.Skill;
import net.medrag.microservices.jaxbexample.xsd.SkillType;
import net.medrag.microservices.jaxbexample.xsd.UnitClass;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.annotation.PostConstruct;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.IOException;
import java.io.StringWriter;
import java.math.BigInteger;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Stanislav Tretyakov
 * 07.01.2021
 */
@Service
public class DocumentService {

    //You must remember, that JaxbContext is thread-safe, while marshallers and unmarshallers,
    // document builders and document builder factories are not!
    private JAXBContext jaxbContext;
    private Schema schema;

    @Value("classpath:xsd/fantasy_fighting.xsd")
    private Resource resource;

    public String buildUnit(net.medrag.microservices.openapiexample.api.model.Unit jsonUnit) {
        StringWriter writer = new StringWriter();
        try {
            net.medrag.microservices.jaxbexample.xsd.Unit unit = new ObjectFactory().createUnit();
            unit.setId(UUID.randomUUID().toString());
            unit.setName(jsonUnit.getName());
            unit.setUnitClass(UnitClass.WARRIOR);
            unit.getSkill().addAll(jsonUnit.getSkillSet().stream().map(this::transformSkill).collect(Collectors.toList()));

            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setSchema(schema);
            marshaller.marshal(unit, writer);

        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return writer.toString();
    }

    @PostConstruct
    public void init() throws JAXBException, IOException, SAXException {

        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        schema = schemaFactory.newSchema(new StreamSource(resource.getInputStream()));
        jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
    }

    private Skill transformSkill(net.medrag.microservices.openapiexample.api.model.Skill jsonSkill) {
        Skill skill = new ObjectFactory().createSkill();
        skill.setName(jsonSkill.getName());
        skill.setPower(BigInteger.valueOf(jsonSkill.getPower()));
        skill.setDuration(BigInteger.valueOf(jsonSkill.getDuration()));
        skill.setType(SkillType.valueOf(jsonSkill.getSkillType().getValue()));
        return skill;
    }
}
