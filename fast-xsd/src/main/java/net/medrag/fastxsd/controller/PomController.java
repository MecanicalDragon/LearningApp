package net.medrag.fastxsd.controller;

import net.medrag.fastxsd.pom.PeriodData;
import net.medrag.fastxsd.service.pom.SomeDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

/**
 * Returns title page. Nothing interesting.
 * <p>
 * {@author} Stanislav Tretyakov
 * 18.09.2018
 */
@Controller
public class PomController {

    private SomeDataService someDataService;

    @Autowired
    public PomController(SomeDataService someDataService) {
        this.someDataService = someDataService;
    }

    @RequestMapping({"/", "/admin"})
    public String title() {
        return "title";
    }

    @RequestMapping("/pom")
    public String pom(Model model) {
        model.addAttribute("someData", someDataService.getAllData());
        model.addAttribute("accountId", "accId");
        model.addAttribute("periodData", new PeriodData(LocalDateTime.now(), LocalDateTime.now().plusDays(3)));
        return "pom";
    }
}
