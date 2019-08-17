package com.mercadolibre.melidnaapi.business.generator;

import com.mercadolibre.melidnaapi.business.service.DnaService;
import com.mercadolibre.melidnaapi.business.validator.HumanDnaValidator;
import com.mercadolibre.melidnaapi.model.table.Dna;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class HumanDnaGenerator {

    private final DnaGenerationPreparator preparator;
    private final List<HumanDnaValidator> validators;
    private final DnaService service;

    public Dna generate(Dna human) {
        preparator.prepare(human);

        validators.forEach(validator -> validator.validate(human));

        human.categorizeAsHuman();

        return service.create(human);
    }

}
