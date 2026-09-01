package ch.bbw.obelix.webshop.service;

import ch.bbw.obelix.quarry.api.QuarryApi;
import ch.bbw.obelix.webshop.dto.BasketDto;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.StandardException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

/**
 * Note that Obelix is definitely not multitasking-capable.
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class QuarryWebclientService {

	private final QuarryApi quarryApi;
	private final BasketService basketService;

	public BasketDto offer(@NonNull BasketDto.BasketItem basketItem) {
		return basketService.offer(basketItem);
	}

	public void leave() {
		basketService.leave();
	}

	public void exchange(UUID menhirId) {
		var menhir = quarryApi.getMenhirById(menhirId);
		var decorativeness = menhir.decorativeness();
		if (basketService.isGoodOffer(decorativeness)) {
			throw new BadOfferException("Bad Offer: That won't even feed Idefix!");
		}
		quarryApi.deleteById(menhirId);
		leave();
	}

	@StandardException
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public static class BadOfferException extends RuntimeException {}
}
