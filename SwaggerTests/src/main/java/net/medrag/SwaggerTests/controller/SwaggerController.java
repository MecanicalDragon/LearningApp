package net.medrag.SwaggerTests.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * {@author} Stanislav Tretyakov
 * 11.11.2019
 */
@Controller
public class SwaggerController {

    @RequestMapping("/openapi")
    public String index() {
        return "redirect:swagger-ui.html";
    }
}
