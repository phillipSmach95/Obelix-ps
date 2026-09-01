package ch.bbw.obelix.webshop.service;

import ch.bbw.obelix.quarry.api.DecorativenessDto;
import ch.bbw.obelix.webshop.dto.BasketDto;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

@Service
public class BasketService {

    private BasketDto basket = BasketDto.empty();

    static <T> List<T> append(List<T> immutableList, T element) {
        var tmpList = new ArrayList<>(immutableList);
        tmpList.add(element);
        return Collections.unmodifiableList(tmpList);
    }

    public BasketDto offer(@NonNull BasketDto.BasketItem basketItem) {
        basket = basket.withItems(append(basket.items(), basketItem));
        return basket;
    }

    public void leave() {
        basket = BasketDto.empty();
    }

    public boolean isGoodOffer(DecorativenessDto decorativeness) {
        var stoneWorth = decorativeness.ordinal();
        var basketWorth = basket.items().stream()
                .map(x -> switch (x.name().toLowerCase(Locale.ROOT)) {
                    case "boar" -> 5;
                    case "honey" -> 2;
                    case "magic potion" -> 0;
                    default -> 1;
                } * x.count())
                .reduce(0, Integer::sum);

        return basketWorth >= stoneWorth;
    }
}