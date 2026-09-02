package ch.bbw.obelix.quarry;

import ch.bbw.obelix.quarry.api.QuarryApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@SpringBootApplication(scanBasePackages = "ch.bbw.obelix")
public class ObelixQuarryApplication {

	static void main(String[] args) {
		SpringApplication.run(ObelixQuarryApplication.class, args);

	}

	@Bean
	QuarryApi quarryApi(@Value("${quarry.base-url}") String baseUrl) {
		WebClient webClient = WebClient.builder()
				.baseUrl(baseUrl)
				.build();

		HttpServiceProxyFactory factory = HttpServiceProxyFactory
				.builderFor(WebClientAdapter.create(webClient))
				.build();

		return factory.createClient(QuarryApi.class);
	}

}
