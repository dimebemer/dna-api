package com.mercadolibre.melidnaapi.controller;

import com.mercadolibre.melidnaapi.business.generator.HumanDnaGenerator;
import com.mercadolibre.melidnaapi.business.generator.SimianDnaGenerator;
import com.mercadolibre.melidnaapi.business.service.DnaStatsService;
import com.mercadolibre.melidnaapi.model.table.Dna;
import com.mercadolibre.melidnaapi.model.table.DnaStats;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DnaController {

    private final SimianDnaGenerator simianGenerator;
    private final HumanDnaGenerator humanGenerator;
    private final DnaStatsService statsService;

    @PostMapping("/simians")
    Dna createSimian(@Validated @RequestBody Dna simian) {
        return simianGenerator.generate(simian);
    }

    @PostMapping("/humans")
    Dna createHuman(@Validated @RequestBody Dna human) {
        return humanGenerator.generate(human);
    }

    @GetMapping("/stats")
    DnaStats getStats() {
        return statsService.findLatest();
    }

}
