package net.medrag.trainings.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * {@author} Stanislav Tretyakov
 * 31.10.2018
 */
@Controller
public class AngViewController {

    @GetMapping("/ang")
    public String getView(){
        return "angular";
    }
}
