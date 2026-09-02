package ch.bbw.obelix.webshop.controller;

import ch.bbw.obelix.webshop.dto.BasketDto;
import ch.bbw.obelix.webshop.service.BasketService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BasketController {
    private final BasketService basketService;

    @PutMapping("/api/basket/offer")
    public BasketDto offer(@RequestBody BasketDto.BasketItem basketItem) {
        return basketService.offer(basketItem);
    }

    @DeleteMapping("/api/basket")
    public void leave() {
        basketService.leave();
    }
}
