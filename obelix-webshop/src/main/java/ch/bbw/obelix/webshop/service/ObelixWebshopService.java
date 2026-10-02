package ch.bbw.obelix.webshop.service;

import ch.bbw.obelix.quarry.api.MenhirDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.experimental.StandardException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.service.annotation.GetExchange;

import java.util.List;
import java.util.UUID;

/**
 * Note that Obelix is definitely not multitasking-capable.
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ObelixWebshopService {

	private final WebClient quarryApiClient;
	private final BasketService basketService;

	@GetExchange("api/menhirs/{menhirId}")
	public MenhirDto getMenhirById(UUID menhirId) {
		return quarryApiClient.get()
				.uri("api/menhirs/{menhirId}", menhirId)
				.retrieve()
				.bodyToMono(MenhirDto.class)
				.block();
	}


	public void exchange(UUID menhirId) {
		var menhir = getMenhirById(menhirId);
		var decorativeness = menhir.decorativeness();
		if (!basketService.isGoodOffer(decorativeness)) {
			throw new BadOfferException("Bad Offer: That won't even feed Idefix!");
		}
		quarryApiClient.delete()
				.uri("api/menhirs/{menhirId}", menhirId)
				.retrieve()
				.toBodilessEntity()
				.block();
		basketService.leave();
	}

	/**
	 * Try to exchange for the first available menhir. Returns true when successful, false otherwise.
	 */
	public boolean exchangeFirstAvailable() {
		if (!basketService.hasItems()) {
			return false;
		}
		var menhirs = quarryApiClient.get()
				.uri("api/menhirs")
				.retrieve()
				.bodyToFlux(MenhirDto.class)
				.collectList()
				.block();
		if (menhirs == null || menhirs.isEmpty()) {
			return false;
		}
		var first = menhirs.get(0);
		var decorativeness = first.decorativeness();
		if (!basketService.isGoodOffer(decorativeness)) {
			return false;
		}
		quarryApiClient.delete()
				.uri("api/menhirs/{menhirId}", first.id())
				.retrieve()
				.toBodilessEntity()
				.block();
		basketService.leave();
		return true;
	}
	@GetExchange("api/menhirs")
	public List<MenhirDto> exchangeFindAll() {

		return quarryApiClient.get()
				.uri("api/menhirs")
				.retrieve()
				.bodyToFlux(MenhirDto.class)
				.collectList()
				.block();
	}


	@StandardException
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public static class BadOfferException extends RuntimeException {}
}
