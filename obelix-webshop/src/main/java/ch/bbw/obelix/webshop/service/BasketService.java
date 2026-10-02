package ch.bbw.obelix.webshop.service;

import ch.bbw.obelix.quarry.api.DecorativenessDto;
import ch.bbw.obelix.quarry.api.MenhirDto;
import ch.bbw.obelix.webshop.dto.BasketDto;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BasketService {
    private BasketDto basket;
    private final WebClient quarryApiClient;
    static <T> List<T> append(List<T> immutableList, T element) {
        var tmpList = new ArrayList<>(immutableList);
        tmpList.add(element);
        return Collections.unmodifiableList(tmpList);
    }
    public BasketDto offer(@NonNull BasketDto.BasketItem basketItem) {
        basket = basket.withItems(append(basket.items(), basketItem));
        return basket;
    }
    @PostConstruct
    public void leave() {
        basket = BasketDto.empty();
    }

    public boolean isGoodOffer(DecorativenessDto decorativeness) {
        var stoneWorth = decorativeness.ordinal();
        var basketWorth = basket.items()
                .stream().map(x -> switch (x.name().toLowerCase(Locale.ROOT)) {
                    case "boar" -> 5; // oh boy, oh boy!
                    case "honey" -> 2;
                    case "magic potion" -> 0; // not allowed to drink this!
                    default -> 1; // everything is worth something
                } * x.count()).reduce(0, Integer::sum);
        log.info("basket worth {} vs menhir worth {} ({})", basketWorth, decorativeness, stoneWorth);
        return basketWorth >= stoneWorth;
    }

    public boolean hasItems() {
        return basket != null && !basket.items().isEmpty();
    }

    public boolean buy(UUID id) {
        var menhir = quarryApiClient.get()
                .uri("api/menhirs/{menhirId}", id)
                .retrieve()
                .bodyToMono(MenhirDto.class)
                .block();
        var ok = isGoodOffer(menhir.decorativeness());
        if (ok) {
            quarryApiClient.delete()
                    .uri("api/menhirs/{menhirId}", id)
                    .retrieve()
                    .toBodilessEntity()
                    .block();
            leave();
        }
        return ok;
    }

    public boolean buyFirstAvailable() {
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
        return buy(first.id());
    }
}
