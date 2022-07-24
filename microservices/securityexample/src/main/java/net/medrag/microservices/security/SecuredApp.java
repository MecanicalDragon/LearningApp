package net.medrag.microservices.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Stanislav Tretyakov
 * 21.10.2021
 */
@SpringBootApplication
public class SecuredApp {
    public static void main(String[] args) {
        SpringApplication.run(SecuredApp.class, args);
    }
}
