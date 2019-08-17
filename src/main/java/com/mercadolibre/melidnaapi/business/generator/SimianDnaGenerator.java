package com.mercadolibre.melidnaapi.business.generator;

import com.mercadolibre.melidnaapi.business.service.DnaService;
import com.mercadolibre.melidnaapi.business.validator.SimianDnaValidator;
import com.mercadolibre.melidnaapi.model.table.Dna;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SimianDnaGenerator {

    private final DnaGenerationPreparator preparator;
    private final List<SimianDnaValidator> validators;
    private final DnaService service;

    public Dna generate(Dna simian) {
        preparator.prepare(simian);

        validators.forEach(validator -> validator.validate(simian));

        simian.categorizeAsSimian();

        return service.create(simian);
    }

}
