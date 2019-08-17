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

    public BigDecimal getRatio() {
        return BigDecimal.valueOf(countMutantDna)
                .divide(BigDecimal.valueOf(countHumanDna), 4, RoundingMode.HALF_UP);
    }

}
