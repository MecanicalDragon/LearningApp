package net.medrag.fastxsd.controller;

import net.medrag.fastxsd.dto.EntryObject;
import net.medrag.fastxsd.service.ArUtilService;
import net.medrag.fastxsd.service.Joker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Simple rest controller for ArUtilService requests
 * <p>
 * {@author} Stanislav Tretyakov
 * 18.09.2018
 */
@PropertySource(value = "classpath:app_russian.properties", encoding = "UTF-8")
@org.springframework.web.bind.annotation.RestController
public class RestController {

    @Value("${app.val}")
    String value;

    private ArUtilService arUtilService;
    private Joker joker;

    @GetMapping("/val")
    String val() {
        System.out.println(value);
        return value;
    }

    @Autowired
    public void setJoker(Joker joker) {
        this.joker = joker;
    }

    @Autowired
    public void setArUtilService(ArUtilService arUtilService) {
        this.arUtilService = arUtilService;
    }

    /**
     * Choosing environment request
     *
     * @param env - chosen environment
     * @return - List of entry objects of chosen environment or null in exception case
     */
    @GetMapping(path = "/getItems", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<EntryObject> getItems(@RequestParam String env) {
        return arUtilService.getEntries(env);
    }

    /**
     * Mass update of xsd validation param for all environment entries
     *
     * @param env    - chosen environment
     * @param change - string new xsd validation status
     * @return - List of entry objects of chosen environment or null in exception case
     */
    @PostMapping(path = "/massChange", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<EntryObject> massChange(@RequestParam String env, @RequestParam String change) {
        return arUtilService.massUpdate(env, change.equals("allOn"));
    }

    /**
     * Update single change
     *
     * @param env    - chosen environment
     * @param change - combined string of entry id and new xsd validation status
     * @return - true if update was successful or null if not
     */
    @PostMapping(path = "/singleChange", produces = MediaType.APPLICATION_JSON_VALUE)
    public String singleChange(@RequestParam String env, @RequestParam String change) {
        return arUtilService.singleUpdate(env, change);
    }

    /**
     * Getting a joke
     *
     * @return - fresh random joke
     */
    @GetMapping(path = "/getJoke", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getJoke() {
        return joker.getJoke();
    }
}
