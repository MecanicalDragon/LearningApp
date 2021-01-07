package net.medrag.microservices.jaxbexample.controller;

import lombok.RequiredArgsConstructor;
import net.medrag.microservices.jaxbexample.service.SkillService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Stanislav Tretyakov
 * 31.12.2020
 */
@RestController
@RequiredArgsConstructor
public class RController {

    private final SkillService skillService;

    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public String getAllSkills(){
        return skillService.getAllSkills();
    }
}
