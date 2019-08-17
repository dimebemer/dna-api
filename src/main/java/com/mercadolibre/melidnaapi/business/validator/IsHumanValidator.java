package com.mercadolibre.melidnaapi.business.validator;

import com.mercadolibre.melidnaapi.business.categorizer.DnaCategorizer;
import com.mercadolibre.melidnaapi.model.exception.NotAllowedException;
import com.mercadolibre.melidnaapi.model.table.Dna;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.mercadolibre.melidnaapi.model.table.Dna.DnaCategory.HUMAN;

@Component
@RequiredArgsConstructor
public class IsHumanValidator implements HumanDnaValidator {

    private final DnaCategorizer categorizer;

    @Override
    public void validate(Dna dna) {
        if (categorizer.categorize(dna) != HUMAN) {
            throw new NotAllowedException("DNA is not of a Human");
        }
    }
}
