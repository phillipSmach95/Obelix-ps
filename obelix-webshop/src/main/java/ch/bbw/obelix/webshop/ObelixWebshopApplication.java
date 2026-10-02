package ch.bbw.obelix.webshop;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "ch.bbw.obelix")
public class ObelixWebshopApplication {
	private static final Log logger = LogFactory.getLog(ObelixWebshopApplication.class);
	static void main(String[] args) {

		SpringApplication.run(ObelixWebshopApplication.class, args);
		logger.info("hello world!!!!");
	}

}
