package net.medrag.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * {@author} Stanislav Tretyakov
 * 14.02.2020
 */
@Controller
public class LandingController {

    @Value("${app.hello.message}")
    static String helloMessage;

    @RequestMapping("/")
    public String landing(){
        System.out.println("landing");
        return "template/landing.html";
    }

    @RequestMapping("/second")
    public String second(){
        System.out.println("second");
        return "second";
    }

    @RequestMapping("/hello")
    @ResponseBody
    public String hello(){
        System.out.println(helloMessage);
        return "Hello";
    }
}
