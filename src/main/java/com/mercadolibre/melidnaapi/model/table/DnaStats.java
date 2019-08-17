package com.mercadolibre.melidnaapi.model.table;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Data
@DynamoDBTable(tableName = "DnaStats")
public class DnaStats {

    @DynamoDBHashKey
    @JsonIgnore
    private String statsId;

    private Long countMutantDna;
    private Long countHumanDna;

    /**
     * Ratio will be null if there are 0 humans since divison by zero cannot happen.
     * Otherwise, ratio of simians x humans will be calculated.
     *
     * @return simians x humans ratio
     */
    public BigDecimal getRatio() {
        if (countHumanDna == 0) {
            return null;
        }

        return BigDecimal.valueOf(countMutantDna)
                .divide(BigDecimal.valueOf(countHumanDna), 4, RoundingMode.HALF_UP);
    }

}
