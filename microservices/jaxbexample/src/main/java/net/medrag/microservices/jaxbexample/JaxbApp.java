package net.medrag.microservices.jaxbexample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author Stanislav Tretyakov
 * 23.12.2020
 */
@SpringBootApplication
@EnableFeignClients(basePackages = "net.medrag.microservices.openapiexample.api")
public class JaxbApp {
    public static void main(String[] args) {
        SpringApplication.run(JaxbApp.class, args);
    }
}
