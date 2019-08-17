package com.mercadolibre.melidnaapi.business.service;

import com.mercadolibre.melidnaapi.model.exception.NotFoundException;
import com.mercadolibre.melidnaapi.model.table.Dna;
import com.mercadolibre.melidnaapi.repository.DnaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DnaService {

    private final DnaRepository repository;

    public Dna create(Dna dna) {
        return repository.save(dna);
    }

    public Dna find(String id) {
        return repository.findById(id)
                .orElseThrow(NotFoundException::new);
    }
}
