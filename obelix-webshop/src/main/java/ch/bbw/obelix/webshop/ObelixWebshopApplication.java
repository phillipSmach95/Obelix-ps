package ch.bbw.obelix.webshop;

import ch.bbw.obelix.quarry.api.QuarryApi;
import ch.bbw.obelix.webshop.controller.ObelixWebshopController;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@SpringBootApplication(scanBasePackages = "ch.bbw.obelix")
public class ObelixWebshopApplication {
	private static final Log logger = LogFactory.getLog(ObelixWebshopApplication.class);
	static void main(String[] args) {

		SpringApplication.run(ObelixWebshopApplication.class, args);
		logger.info("hello world!!!!");
	}
	@Bean
	@Profile("!test")
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
