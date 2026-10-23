package ch.bbw.obelix.webshop.service;

import ch.bbw.obelix.quarry.api.QuarryApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class ServiceConfig {

    @Bean
    public QuarryApi remoteQuarryApi(WebClient quarryApiClient) {
        return HttpServiceProxyFactory.builderFor(WebClientAdapter.create(quarryApiClient))
                .build()
                .createClient(QuarryApi.class);
    }

    @Bean
    public WebClient quarryApiClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:8081") // Replace with the actual URL of the Quarry API
                .build();
    }
}
