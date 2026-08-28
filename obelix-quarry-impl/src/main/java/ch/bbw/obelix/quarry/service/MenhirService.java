package ch.bbw.obelix.quarry.service;

import ch.bbw.obelix.quarry.model.MenhirEntity;
import ch.bbw.obelix.quarry.repository.MenhirRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.experimental.StandardException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MenhirService {
    private final MenhirRepository menhirRepository;

    @PostConstruct
    public void initializeMenhirs() {
        // Only initialize if the database is empty
        if (menhirRepository.count() == 0) {
            createDefaultMenhirs();
        }
    }

    public void createDefaultMenhirs() {
        menhirRepository.deleteAll();

        var obelixSpecial = new MenhirEntity();
        obelixSpecial.setWeight(2.5);
        obelixSpecial.setStoneType("Granite Gaulois");
        obelixSpecial.setDecorativeness(MenhirEntity.Decorativeness.DECORATED);
        obelixSpecial.setDescription("Obelix's personal favorite! Perfect for throwing at Romans. ");
        menhirRepository.save(obelixSpecial);

        var getafixMasterpiece = new MenhirEntity();
        getafixMasterpiece.setWeight(4.2);
        getafixMasterpiece.setStoneType("Mystical Dolmen Stone");
        getafixMasterpiece.setDecorativeness(MenhirEntity.Decorativeness.MASTERWORK);
        getafixMasterpiece.setDescription("Blessed by Getafix himself! This menhir is rumored to " +
                "enhance magic potion brewing. Side effects may include: sudden urge to fight Romans.");
        menhirRepository.save(getafixMasterpiece);

        var touristTrap = new MenhirEntity();
        touristTrap.setWeight(1.0);
        touristTrap.setStoneType("Imported Roman Marble");
        touristTrap.setDecorativeness(MenhirEntity.Decorativeness.PLAIN);
        touristTrap.setDescription("Budget-friendly option! Made from 'liberated' Roman materials. " +
                "Perfect for beginners or those who just want to annoy Caesar. Asterix approved!");
        menhirRepository.save(touristTrap);
    }

}
