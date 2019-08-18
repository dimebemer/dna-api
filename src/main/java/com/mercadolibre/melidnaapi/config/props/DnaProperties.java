package com.mercadolibre.melidnaapi.config.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("dna")
public class DnaProperties {

    private char[] nitrogenousBases;
    private Integer minimumSimianSequences;
    private Integer simianSequenceSize;

    public int getMinimumMatrixSize() {
        return nitrogenousBases.length;
    }

}
