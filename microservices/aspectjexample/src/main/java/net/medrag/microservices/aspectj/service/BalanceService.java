package net.medrag.microservices.aspectj.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Stanislav Tretyakov
 * 13.12.2021
 */
@Service
public class BalanceService {

    public AtomicInteger balance = new AtomicInteger(1000);

    public int increaseBalance(int delta) {
        System.out.println("increasing balance by " + delta);
        return balance.addAndGet(delta);
    }

    public int decreaseBalance(int delta) {
        System.out.println("decreasing balance by " + delta);
        return balance.addAndGet(delta * -1);
    }
}
