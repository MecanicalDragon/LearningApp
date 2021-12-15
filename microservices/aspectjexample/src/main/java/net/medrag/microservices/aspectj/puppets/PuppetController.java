package net.medrag.microservices.aspectj.puppets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Stanislav Tretyakov
 * 15.12.2021
 */
@RestController
@RequestMapping("/puppet")
public class PuppetController {

    @Autowired
    private PuppetService puppetService;

    @GetMapping("/{name}")
    public void addPuppet(@PathVariable String name) {
        puppetService.addPuppet(name);
    }

    @GetMapping("/{name}/{hash}")
    public void addPuppetsHash(@PathVariable String name, @PathVariable String hash) {
        puppetService.setHash(name, hash);
    }

    @GetMapping("/print")
    public void printPuppets() {
        puppetService.printPuppets();
    }
}
