package net.medrag.microservices.aspectj.service;

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
@RequestMapping("/aspect")
public class AspectController {

    @Autowired
    private BalanceService balanceService;

    @GetMapping("/inc/{inc}")
    public int inc(@PathVariable int inc) {
        return balanceService.increaseBalance(inc);
    }

    @GetMapping("/dec/{dec}")
    public int dec(@PathVariable int dec) {
        return balanceService.decreaseBalance(dec);
    }
}
