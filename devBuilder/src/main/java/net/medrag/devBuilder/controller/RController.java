package net.medrag.devBuilder.controller;

import net.medrag.devBuilder.DevBuilderApplication;
import net.medrag.devBuilder.model.Request;
import net.medrag.devBuilder.model.StartUpData;
import net.medrag.devBuilder.service.Joker;
import net.medrag.devBuilder.service.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * {@author} Stanislav Tretyakov
 * 10.10.2019
 */
@RestController
@RequestMapping("/devBuilder")
public class RController {

    private Processor processor;
    private Joker joker;

    @Autowired
    public RController(Processor processor, Joker joker) {
        this.processor = processor;
        this.joker = joker;
    }

    @GetMapping(value = "/getDeveloper/{taskId}", produces = MediaType.TEXT_XML_VALUE)
    public String getDev(@PathVariable("taskId") String taskId) {
        return processor.process(taskId);
    }

    @PostMapping(value = "/setSkillsCount", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Request> setServicesAmount(@RequestBody @Valid Request request) {
        return ResponseEntity.ok(processor.setServicesAmount(request));
    }

    @DeleteMapping("/removeRequest")
    public ResponseEntity<Request> removeRequest(@RequestParam String ending) {
        return ResponseEntity.ok(processor.removeRequest(ending));
    }

    @GetMapping(value = "/getStartUpData", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<StartUpData> getStartUpData() {
        return ResponseEntity.ok(processor.getStartUpData());
    }

    /**
     * Getting a joke
     *
     * @return - fresh random joke
     */
    @GetMapping(path = "/getJoke", produces = MediaType.TEXT_PLAIN_VALUE)
    public String getJoke() {
        return joker.getJoke();
    }

    @GetMapping("/shutdown")
    public void shutdown() {
        DevBuilderApplication.shutDown();
    }


}
