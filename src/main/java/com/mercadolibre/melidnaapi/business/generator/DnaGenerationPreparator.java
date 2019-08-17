package com.mercadolibre.melidnaapi.business.generator;

import com.mercadolibre.melidnaapi.business.validator.DnaValidator;
import com.mercadolibre.melidnaapi.model.table.Dna;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DnaGenerationPreparator {

    private final List<DnaValidator> validators;

    public void prepare(Dna dna) {
        prepareFields(dna);
        validate(dna);
    }

    private void prepareFields(Dna dna) {
        dna.generateId();
        dna.prepareMatrixLetters();
    }

    private void validate(Dna dna) {
        validators.forEach(validator -> validator.validate(dna));
    }

}
