package com.mercadolibre.melidnaapi.model.table;


import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

public class DnaStatsTest {

    @Test
    public void givenStatsWith0Humans_shouldHaveNullRatio() {
        // given
        DnaStats stats = new DnaStats();
        stats.setCountMutantDna(12L);
        stats.setCountHumanDna(0L);

        // when
        BigDecimal ratio = stats.getRatio();

        // then
        assertThat(ratio, nullValue());
    }

    @Test
    public void givenStatsWithAtLeast1Human_shouldHaveProperRatio() {
        // given
        DnaStats stats = new DnaStats();
        stats.setCountMutantDna(14L);
        stats.setCountHumanDna(134L);

        // when
        BigDecimal ratio = stats.getRatio();

        // then
        assertThat(ratio, comparesEqualTo(new BigDecimal("0.1045")));
    }

}