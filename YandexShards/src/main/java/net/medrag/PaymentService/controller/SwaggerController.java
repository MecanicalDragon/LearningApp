package net.medrag.PaymentService.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Just suggests convenient link to openApi documentation
 */
@Controller
@ApiIgnore
public class SwaggerController {

    @RequestMapping("/openapi")
    public String index() {
        return "redirect:swagger-ui.html";
    }
}
