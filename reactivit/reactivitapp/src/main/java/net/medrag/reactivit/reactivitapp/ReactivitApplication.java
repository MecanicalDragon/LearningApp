package net.medrag.reactivit.reactivitapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

@SpringBootApplication
public class ReactivitApplication {

    @Bean
    public WebClient webClient() {
        ConnectionProvider connectionProvider = ConnectionProvider.builder("myConnectionPool")
            .maxConnections(2000)
            .pendingAcquireMaxCount(5000)
            .build();
        return WebClient.builder().clientConnector(new ReactorClientHttpConnector(HttpClient.create(connectionProvider))).build();
    }

    public static void main(String[] args) {
        SpringApplication.run(ReactivitApplication.class, args);
    }

}
