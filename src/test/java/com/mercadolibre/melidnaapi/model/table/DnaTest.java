package com.mercadolibre.melidnaapi.model.table;

import org.junit.Test;

import static com.mercadolibre.melidnaapi.model.table.Dna.DnaCategory.HUMAN;
import static com.mercadolibre.melidnaapi.model.table.Dna.DnaCategory.SIMIAN;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class DnaTest {

    @Test
    public void givenDnasWithSameMatrix_shouldGenerateSameId() {
        // given
        Dna dna1 = buildDna1();
        Dna dna2 = buildDna1();

        // when
        dna1.generateId();
        dna2.generateId();

        // then
        assertThat(dna1.getDnaId(), is("d4c29ecd-4162-3e72-95b9-042c03be0edf"));
        assertThat(dna2.getDnaId(), is("d4c29ecd-4162-3e72-95b9-042c03be0edf"));
    }

    @Test
    public void givenDnasWithDifferentMatrix_shouldGenerateDifferentId() {
        // given
        Dna dna1 = buildDna1();
        Dna dna2 = buildDna2();

        // when
        dna1.generateId();
        dna2.generateId();

        // then
        assertThat(dna1.getDnaId(), is("d4c29ecd-4162-3e72-95b9-042c03be0edf"));
        assertThat(dna2.getDnaId(), is("daa5532e-c355-3fde-a8a1-27c413520423"));
    }

    @Test
    public void givenADna_whenPreparingLetters_shouldMakeLettersUppercase() {
        // given
        Dna dna = new Dna();
        dna.setDna(asList(
                "CTGagA",
                "CTGaGC",
                "TATTGT",
                "AGaggg",
                "CCCCTA",
                "TCACTG"
        ));

        // when
        dna.prepareMatrixLetters();

        // then
        assertThat(dna.getDna(), is(asList(
                "CTGAGA",
                "CTGAGC",
                "TATTGT",
                "AGAGGG",
                "CCCCTA",
                "TCACTG"
        )));
    }

    @Test
    public void givenADna_shouldCategorizeAsSimian() {
        // given
        Dna dna = buildDna1();

        // when
        dna.categorizeAsSimian();

        // then
        assertThat(dna.getCategory(), is(SIMIAN));
    }

    @Test
    public void givenADna_shouldCategorizeAsHuman() {
        // given
        Dna dna = buildDna1();

        // when
        dna.categorizeAsHuman();

        // then
        assertThat(dna.getCategory(), is(HUMAN));
    }

    private Dna buildDna1() {
        Dna dna1 = new Dna();
        dna1.setDna(asList(
                "CTGAGA",
                "CTGAGC",
                "TATTGT",
                "AGAGGG",
                "CCCCTA",
                "TCACTG"
        ));

        return dna1;
    }

    private Dna buildDna2() {
        Dna dna2 = new Dna();
        dna2.setDna(asList(
                "CTGAGA",
                "CTGAGC",
                "TATTGT",
                "AGAGGG",
                "CCCCTA",
                "TCACTA"
        ));
        return dna2;
    }

}