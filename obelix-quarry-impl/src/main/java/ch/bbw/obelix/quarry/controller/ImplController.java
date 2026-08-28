package ch.bbw.obelix.quarry.controller;
import ch.bbw.obelix.quarry.api.MenhirDto;
import ch.bbw.obelix.quarry.model.MenhirEntity;
import ch.bbw.obelix.quarry.repository.MenhirRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.StandardException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ImplController {
    private final MenhirRepository menhirRepository;

    @GetMapping("/api")
    public String welcome() {
        return "Welcome to Obelix's Menhir Shop! The finest menhirs in all of Gaul! Ces Romains sont fous!";
    }

    @GetMapping("/api/menhirs")
    public List<MenhirDto> getAllMenhirs() {
        return menhirRepository.findAll()
                .stream().map(MenhirEntity::toDto).toList();
    }

    @GetMapping("/api/menhirs/{menhirId}")
    public MenhirDto getMenhirById(@PathVariable UUID menhirId) {
        return menhirRepository.findById(menhirId)
                .map(MenhirEntity::toDto)
                .orElseThrow(() -> new UnknownMenhirException("unknown menhir with id " + menhirId));
    }

    /**
     * Note that this should only be called by Asterix himself. Hopefully, no customer will ever find this endpoint...
     */
    @DeleteMapping("/api/quarry/{menhirId}")
    public void deleteById(@PathVariable UUID menhirId) {
        menhirRepository.deleteById(menhirId);
    }

    @StandardException
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public static class UnknownMenhirException extends RuntimeException {}

}
