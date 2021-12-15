package net.medrag.microservices.aspectj.tracked;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Stanislav Tretyakov
 * 13.12.2021
 */
@RestController
@RequestMapping("/tracked")
public class TrackedController {

    @Autowired
    private TrackedPuppetService trackedPuppetService;

    @GetMapping("/{surname}/{name}/{mid}")
    public void addPuppet(@PathVariable String name, @PathVariable String surname, @PathVariable String mid) {
        trackedPuppetService.updatePuppet(surname, name, mid);
    }

    @GetMapping("/print")
    public void printPuppets() {
        trackedPuppetService.printPuppets();
    }
}
