package ch.bbw.obelix.webshop.controller;

import ch.bbw.obelix.webshop.service.ObelixWebshopService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.UUID;

@RestController
@RequestMapping("/api/basket")
@RequiredArgsConstructor
public class ObelixWebshopController {

	private final ObelixWebshopService obelixWebshopService;

	@PostMapping("/buy/{id}")
	public void exchangeFor(@PathVariable UUID id) {
		obelixWebshopService.exchange(id);
	}

	@PostMapping({"/buy", "/buy/"})
	public void exchangeNoId() {
		var success = obelixWebshopService.exchangeFirstAvailable();
		if (!success) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
	}

}
