package ch.bbw.obelix.webshop.controller;

import ch.bbw.obelix.quarry.api.QuarryApi;
import ch.bbw.obelix.webshop.dto.BasketDto;
import ch.bbw.obelix.webshop.service.BasketService;
import ch.bbw.obelix.webshop.service.QuarryWebclientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ObelixWebshopController {
	private final QuarryWebclientService quarryWebclientService;
	private final BasketService basketService;
	private final QuarryApi quarryApi;

	/**
	 * Customer adds even more shinies in exchange for a beautiful menhir.
	 */
	@PutMapping("/basket/offer")
	public BasketDto offer(@RequestBody BasketDto.BasketItem basketItem) {
		return basketService.offer(basketItem);
	}

	/**
	 * In case the customer doesn't want to offer more and leaves.
	 */
	@DeleteMapping("/basket")
	public void leave() {
		basketService.leave();
	}

	/**
	 * Decide if the current basket is worthy enough for a beautiful menhir.
	 *
	 * @param menhirId the menhir to buy
	 */
	@PostMapping("/basket/buy/{menhirId}")
	public void exchange(UUID menhirId) {
		var menhir = quarryApi.getMenhirById(menhirId);
		if (basketService.isGoodOffer(menhir.decorativeness())) {
			throw new QuarryWebclientService.BadOfferException("Bad Offer: That won't even feed Idefix!");
		}
		quarryApi.deleteById(menhirId);
		basketService.leave();
	}

}
