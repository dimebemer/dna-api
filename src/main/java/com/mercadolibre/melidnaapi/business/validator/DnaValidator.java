package com.mercadolibre.melidnaapi.business.validator;

import com.mercadolibre.melidnaapi.model.exception.NotAllowedException;
import com.mercadolibre.melidnaapi.model.table.Dna;
import com.mercadolibre.melidnaapi.repository.DnaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DnaValidator {

    private final DnaRepository repository;

    public void validate(Dna dna) {
        validateIfExists(dna);
    }

    private void validateIfExists(Dna dna) {
        if (repository.existsById(dna.getDnaId())) {
            throw new NotAllowedException("This DNA already exists");
        }
    }

}
