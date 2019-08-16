package com.mercadolibre.melidnaapi.model.table;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConvertedEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mercadolibre.melidnaapi.model.exception.NotAllowedException;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@DynamoDBTable(tableName = "Dna")
public class Dna {

    @DynamoDBHashKey
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String dnaId;

    @DynamoDBTypeConvertedEnum
    private DnaCategory category;

    private List<String> dna;

    public void generateId() {
        if (dna == null) {
            throw new NotAllowedException("DNA code has not been set");
        }

        this.dnaId = UUID.nameUUIDFromBytes(String.join(",", this.dna).getBytes()).toString();
    }

    public void categorizeAsSimian() {
        setCategory(DnaCategory.SIMIAN);
    }

    public void categorizeAsHuman() {
        setCategory(DnaCategory.HUMAN);
    }

    enum DnaCategory {
        HUMAN,
        SIMIAN
    }

}
