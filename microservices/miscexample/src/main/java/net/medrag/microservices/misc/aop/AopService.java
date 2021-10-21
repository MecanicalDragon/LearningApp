package net.medrag.microservices.misc.aop;

import org.springframework.stereotype.Service;

/**
 * @author Stanislav Tretyakov
 * 21.10.2021
 */
@Service
public class AopService {

    @AspectIntercepted(printBefore = "before...", printAfter = "after...")
    public String sayHello(String name) {
        System.out.println("Hello " + name);
        return "Hello " + name;
    }
}
