package net.medrag.fastxsd.service;

import net.medrag.fastxsd.dto.EntryObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * Service for ARS requests.
 *
 * {@author} Stanislav Tretyakov
 * 19.09.2018
 */
@Service
public class ArUtilService {

    private static final Logger LOGGER = LogManager.getLogger(ArUtilService.class);

    private static final List<String> DIENST = new ArrayList<>();
    private static final List<String> DIENSTAUSPRAEGUNG = new ArrayList<>();
    private static final List<EntryObject> SCOPED = new ArrayList<>();
    private static final String SUCCESS = "SUCCESS";
    private static final String UNCHANGED = "UNCHANGED";

    private static Integer timeout;

    /**
     * Initialize map with environment parameters
     */
    @PostConstruct
    private void init() {

        DIENST.add("Shitty");
        DIENST.add("Dog");
        DIENST.add("Pussy");
        DIENST.add("Ghost");
        DIENST.add("Dildo");
        DIENST.add("Wet");
        DIENST.add("Holy");
        DIENST.add("LSD");
        DIENST.add("Snake");
        DIENST.add("Boss");

        DIENSTAUSPRAEGUNG.add("Penetrator");
        DIENSTAUSPRAEGUNG.add("Murderer");
        DIENSTAUSPRAEGUNG.add("Fucker");
        DIENSTAUSPRAEGUNG.add("Annihilator");
        DIENSTAUSPRAEGUNG.add("Dancer");
        DIENSTAUSPRAEGUNG.add("Eater");
        DIENSTAUSPRAEGUNG.add("Spanker");
        DIENSTAUSPRAEGUNG.add("Prayer");
        DIENSTAUSPRAEGUNG.add("Tormentor");
        DIENSTAUSPRAEGUNG.add("Overkiller");
    }

    /**
     * Extracting values from ars.
     *
     * @param env - required environment
     * @return - DTO-s list of required entries or null in exception case
     */
    public List<EntryObject> getEntries(String env) {

        LOGGER.info("environment:  " + env);
        SCOPED.clear();
        List<EntryObject> dtos = new ArrayList<>();

//            Get timeout in seconds
        if (timeout == null) {
            timeout = 10;
            LOGGER.info("Current timeout: " + timeout);
        }

        Collections.shuffle(DIENST);
        Collections.shuffle(DIENSTAUSPRAEGUNG);

//            Cast entries to DTO-s
        for (int i = 0; i < 10; i++) {
            EntryObject eo = new EntryObject();
            eo.setId(String.valueOf(i));
            eo.setDienst(DIENST.get(i));
            eo.setDienstauspraegung(DIENSTAUSPRAEGUNG.get(i));
            eo.setAnwendsungsname(String.format("%s_%s", DIENST.get(i), DIENSTAUSPRAEGUNG.get(i)));
            eo.setSwitched(new Random().nextInt() > 0.5);
            dtos.add(eo);
            SCOPED.add(eo);
        }

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return dtos;
    }

    /**
     * Mass entry update (All on or all of xsd validation)
     *
     * @param env   - required environment
     * @param onOff - 'true' if on for all, 'false' if off
     * @return - DTO-s list of required entries or null in exception case
     */
    public List<EntryObject> massUpdate(String env, boolean onOff) {

//        Filtering entries and call 'singleUpdate' for each, that in not switched to required XSD option
        for (EntryObject dto : SCOPED) {
            if (dto.isSwitched() != onOff) {
                String comment = singleUpdate(env, String.format("%s-%s-%s", dto.getId(), onOff ? "on" : "off", dto.getAnwendsungsname()));
                dto.setComment(comment);
                if (comment.equals(SUCCESS)) {
                    dto.setSwitched(onOff);
                }
            } else {
                dto.setComment(UNCHANGED);
            }
        }

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return SCOPED;
    }

    /**
     * Update single entry in ars
     *
     * @param env     - chosen environment
     * @param changes - combined string of entry id, new xsd validation status and applikationscode, separated by '-'
     * @return true if update was successful or null if not
     */
    public String singleUpdate(String env, String changes) {

        LOGGER.info("environment=" + env + ", changes=" + changes);

//            XSD validation update
        SCOPED.get(new Integer(changes.split("-")[0])).setSwitched(changes.split("-")[1].equals("off"));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (new Random().nextInt() > 0.85){
            return "XSD-update wasn't successful.";
        }
        return SUCCESS;
    }
}
