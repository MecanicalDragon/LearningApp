package net.medrag.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * {@author} Stanislav Tretyakov
 * 14.02.2020
 */
@Controller
// ${CATALINA_HOME}/bin in WAR-case
@PropertySource("classpath:application.properties")
public class LandingController {

    @Autowired
    Environment environment;

    @Value("${app.hello.message}")
    static String helloMessage;

    @RequestMapping("/")
    public String landing() {
        return "template/landing.html";
    }

    @RequestMapping("/second")
    public String second() {
        System.out.println("second");
        return "second";
    }

    @RequestMapping("/hello")
    @ResponseBody
    public String hello() {
        System.out.println(helloMessage);
        System.out.println(environment.getProperty("app.hello.message"));
        System.out.println(environment.getProperty("classpath"));
        return "Hello";
    }
}
