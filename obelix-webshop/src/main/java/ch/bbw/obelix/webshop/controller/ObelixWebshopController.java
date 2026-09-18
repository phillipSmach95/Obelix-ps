package ch.bbw.obelix.webshop.controller;

import ch.bbw.obelix.quarry.api.MenhirDto;
import ch.bbw.obelix.webshop.service.ObelixWebshopService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
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

	@GetMapping("/api/menhirs")
	public List<MenhirDto> getMenhirs(){
        return obelixWebshopService.exchangeFindAll();
	}
	@PostMapping({"/buy", "/buy/"})
	public void exchangeNoId() {
		var success = obelixWebshopService.exchangeFirstAvailable();
		if (!success) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
	}

}
