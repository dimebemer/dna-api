package com.mercadolibre.melidnaapi.business.validator;

import com.mercadolibre.melidnaapi.business.categorizer.DnaCategorizer;
import com.mercadolibre.melidnaapi.model.exception.BusinessException;
import com.mercadolibre.melidnaapi.model.table.Dna;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.mercadolibre.melidnaapi.model.table.Dna.DnaCategory.SIMIAN;

@Component
@RequiredArgsConstructor
public class IsSimianValidator implements SimianValidator {

    private final DnaCategorizer categorizer;

    @Override
    public void validate(Dna dna) {
        if (categorizer.categorize(dna) != SIMIAN) {
            throw new BusinessException("DNA is not of a Simian");
        }
    }
}
