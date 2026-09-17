package ch.bbw.obelix.webshop;

import ch.bbw.obelix.quarry.api.DecorativenessDto;
import ch.bbw.obelix.quarry.api.MenhirDto;
import ch.bbw.obelix.quarry.api.QuarryApi;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@TestConfiguration
public class TestQuarryApiConfig {

    @Bean
    @Primary
    public QuarryApi quarryApi() {
        return new QuarryApi() {
            private final List<MenhirDto> menhirs = new ArrayList<>(List.of(
                    new MenhirDto(UUID.randomUUID(), 12.3, "granite", DecorativenessDto.PLAIN, "a plain menhir"),
                    new MenhirDto(UUID.randomUUID(), 45.0, "basalt", DecorativenessDto.MASTERWORK, "a fancy menhir")
            ));

            @Override
            public String welcome() {
                return "test-quarry";
            }

            @Override
            public MenhirDto getMenhirById(UUID menhirId) {
                return menhirs.stream().filter(m -> m.id().equals(menhirId)).findFirst()
                        .orElseThrow(() -> new RuntimeException("unknown menhir"));
            }

            @Override
            public List<MenhirDto> getAllMenhirs() {
                return new ArrayList<>(menhirs);
            }

            @Override
            public void deleteById(UUID menhirId) {
                menhirs.removeIf(m -> m.id().equals(menhirId));
            }
        };
    }
}
