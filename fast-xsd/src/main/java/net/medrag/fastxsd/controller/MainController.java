package net.medrag.fastxsd.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Returns title page. Nothing interesting.
 * <p>
 * {@author} Stanislav Tretyakov
 * 18.09.2018
 */
@Controller
public class MainController {
    @RequestMapping({"/", "/admin"})
    public String index() {

        return "title";
    }
}
