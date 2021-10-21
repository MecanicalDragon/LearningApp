package net.medrag.microservices.misc.bpp;

import org.springframework.stereotype.Service;

/**
 * @author Stanislav Tretyakov
 * 21.10.2021
 */
@Service
public class BppService implements BppServiceApi {

    @Override
    @BppIntercepted(printBefore = "before...", printAfter = "after...")
    public String sayHello(String name) {
        System.out.println("Hello " + name);
        return "Hello " + name;
    }

    @Override
    @BppIntercepted(printBefore = "before again...", printAfter = "after again...")
    public String sayHelloAgain(String name) {
        System.out.println("Hello " + name);
        return "Hello " + name;
    }

    @Override
    public String keepSilence(String name) {
        System.out.println("Hello " + name);
        return "Hello " + name;
    }
}
