package net.medrag.microservices.misc.bpp;

/**
 * @author Stanislav Tretyakov
 * 21.10.2021
 */
public interface BppServiceApi {
    String sayHello(String name);

    String sayHelloAgain(String name);

    String keepSilence(String name);
}
