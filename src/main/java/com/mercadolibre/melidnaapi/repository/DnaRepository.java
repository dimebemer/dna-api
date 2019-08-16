package com.mercadolibre.melidnaapi.repository;

import com.mercadolibre.melidnaapi.model.table.Dna;
import org.socialsignin.spring.data.dynamodb.repository.DynamoDBCrudRepository;

public interface DnaRepository extends DynamoDBCrudRepository<Dna, String> {
}
