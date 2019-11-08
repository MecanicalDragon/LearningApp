package net.medrag.devBuilder.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * {@author} Stanislav Tretyakov
 * 11.10.2019
 */
@Controller
@RequestMapping({"/devBuilder", "/"})
public class SController {

    @GetMapping("/gui")
    public String index(){
        return "index.html";
    }
}
