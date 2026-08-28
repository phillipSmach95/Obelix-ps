package ch.bbw.obelix.quarry.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.List;
import java.util.UUID;

@HttpExchange
public interface QuarryApi {

    @GetExchange("/api")
    String welcome();
    @GetExchange("/api/menhir/{menhirId}")
    MenhirDto getMenhirById(@PathVariable UUID menhirId);

    @GetExchange("/api/menhirs")
    List<MenhirDto> getAllMenhirs();
    @DeleteExchange("/api/quarry/{menhirId}")
    void deleteById(@PathVariable UUID menhirId);




}
