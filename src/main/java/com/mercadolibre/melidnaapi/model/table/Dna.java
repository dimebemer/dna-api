package com.mercadolibre.melidnaapi.model.table;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConvertedEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Data
@DynamoDBTable(tableName = "Dna")
public class Dna {

    @DynamoDBHashKey
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String dnaId;

    @DynamoDBTypeConvertedEnum
    private DnaCategory category;

    @NotEmpty
    private List<String> dna = new ArrayList<>();

    public void generateId() {
        setDnaId(UUID.nameUUIDFromBytes(String.join(",", this.dna).getBytes()).toString());
    }

    public void prepareMatrixLetters() {
        setDna(dna.stream()
                .map(String::toUpperCase)
                .collect(toList())
        );
    }

    public void categorizeAsSimian() {
        setCategory(DnaCategory.SIMIAN);
    }

    public void categorizeAsHuman() {
        setCategory(DnaCategory.HUMAN);
    }

    public enum DnaCategory {
        HUMAN,
        SIMIAN
    }

}
