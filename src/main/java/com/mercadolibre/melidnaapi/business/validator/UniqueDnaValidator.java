package com.mercadolibre.melidnaapi.business.validator;

import com.mercadolibre.melidnaapi.model.exception.BusinessException;
import com.mercadolibre.melidnaapi.model.table.Dna;
import com.mercadolibre.melidnaapi.repository.DnaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UniqueDnaValidator implements DnaValidator {

    private final DnaRepository repository;

    @Override
    public void validate(Dna dna) {
        if (repository.existsById(dna.getDnaId())) {
            throw new BusinessException("This DNA already exists");
        }
    }

}
