package com.mercadolibre.melidnaapi.repository;

import com.mercadolibre.melidnaapi.model.table.DnaStats;
import org.socialsignin.spring.data.dynamodb.repository.DynamoDBCrudRepository;

public interface DnaStatsRepository extends DynamoDBCrudRepository<DnaStats, String> {
}
