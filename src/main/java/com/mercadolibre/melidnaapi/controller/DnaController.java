package com.mercadolibre.melidnaapi.controller;

import com.mercadolibre.melidnaapi.business.generator.HumanDnaGenerator;
import com.mercadolibre.melidnaapi.business.generator.SimianDnaGenerator;
import com.mercadolibre.melidnaapi.business.service.DnaService;
import com.mercadolibre.melidnaapi.business.service.DnaStatsService;
import com.mercadolibre.melidnaapi.model.table.Dna;
import com.mercadolibre.melidnaapi.model.table.DnaStats;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dnas")
public class DnaController {

    private final SimianDnaGenerator simianGenerator;
    private final HumanDnaGenerator humanGenerator;
    private final DnaService dnaService;
    private final DnaStatsService statsService;

    @GetMapping("/{id}")
    Dna find(@PathVariable String id) {
        return dnaService.find(id);
    }

    @GetMapping("/stats")
    DnaStats getStats() {
        return statsService.findLatest();
    }

    @PostMapping("/simians")
    Dna createSimian(@Validated @RequestBody Dna simian) {
        return simianGenerator.generate(simian);
    }

    @PostMapping("/humans")
    Dna createHuman(@Validated @RequestBody Dna human) {
        return humanGenerator.generate(human);
    }

}
