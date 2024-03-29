package com.mercadolibre.melidnaapi.business.service;

import com.mercadolibre.melidnaapi.model.exception.NotFoundException;
import com.mercadolibre.melidnaapi.model.table.DnaStats;
import com.mercadolibre.melidnaapi.repository.DnaStatsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DnaStatsService {

    private static final String LATEST_ID = "latest";

    private final DnaStatsRepository repository;

    public DnaStats findLatest() {
        return repository.findById(LATEST_ID)
                .orElseThrow(NotFoundException::new);
    }

}
