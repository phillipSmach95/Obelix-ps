package ch.bbw.obelix.webshop.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ServiceConfig {

    @Bean
    public WebClient quarryApiClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:8081") // Replace with the actual URL of the Quarry API
                .build();
    }
}
