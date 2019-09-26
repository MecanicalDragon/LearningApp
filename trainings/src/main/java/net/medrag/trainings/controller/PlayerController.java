package net.medrag.trainings.controller;

import net.medrag.trainings.model.Constraits;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * {@author} Stanislav Tretyakov
 * 13.11.2018
 */
@RestController
public class PlayerController {

    @GetMapping("/getHP")
    public Integer getStartHP(HttpServletRequest request){
        request.getSession().setAttribute("p1hp", Constraits.START_HP);
        request.getSession().setAttribute("p2hp", Constraits.START_HP);
        return Constraits.START_HP;
    }
}
