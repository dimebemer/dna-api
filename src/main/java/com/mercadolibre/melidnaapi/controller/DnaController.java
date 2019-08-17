package com.mercadolibre.melidnaapi.controller;

import com.mercadolibre.melidnaapi.business.generator.SimianDnaGenerator;
import com.mercadolibre.melidnaapi.model.table.Dna;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DnaController {

    private final SimianDnaGenerator simianGenerator;

    @PostMapping("/simians")
    Dna createSimian(@Validated @RequestBody Dna simian) {
        return simianGenerator.generate(simian);
    }

}
