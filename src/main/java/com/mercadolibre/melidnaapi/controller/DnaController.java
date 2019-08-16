package com.mercadolibre.melidnaapi.controller;

import com.mercadolibre.melidnaapi.model.table.Dna;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DnaController {



    @PostMapping("/simians")
    void createSimians(@RequestBody Dna simian) {
        simian.generateId();
    }

}
