package com.mercadolibre.melidnaapi.business.categorizer;

import com.mercadolibre.melidnaapi.config.props.DnaProperties;
import com.mercadolibre.melidnaapi.model.table.Dna;
import com.mercadolibre.melidnaapi.model.table.Dna.DnaCategory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static com.mercadolibre.melidnaapi.model.table.Dna.DnaCategory.HUMAN;
import static com.mercadolibre.melidnaapi.model.table.Dna.DnaCategory.SIMIAN;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DnaCategorizerTest {

    @InjectMocks
    private DnaCategorizer categorizer;

    @Mock
    private DnaProperties props;

    @Before
    public void setUp() {
        when(props.getSimianSequenceSize()).thenReturn(4);
        when(props.getMinimumSimianSequences()).thenReturn(2);
    }

    @Test
    public void givenAMatrixWith1SequenceOfLetters_shouldReturnHumanCategory() {
        // given
        Dna dna = new Dna();
        dna.setDna(asList(
                "CTGAGA",
                "CTGAGC",
                "TATTGT",
                "AGAGAG",
                "CCCCTA", // horizontal C
                "TCACTG"
        ));

        // when
        DnaCategory category = categorizer.categorize(dna);

        // then
        assertThat(category, is(HUMAN));
    }

    @Test
    public void givenAMatrixWith2HorizontalSequences_shouldReturnSimianCategory() {
        // given
        Dna dna = new Dna();
        dna.setDna(asList(
                "CTGAGA",
                "CTGAGC",
                "TTTTGA", // horizontal T
                "AGAGTG",
                "CCCCAA", // horizontal G
                "TCACTG"
        ));

        // when
        DnaCategory category = categorizer.categorize(dna);

        // then
        assertThat(category, is(SIMIAN));
    }

    @Test
    public void givenAMatrixWith2VerticalSequences_shouldReturnSimianCategory() {
        // given
        Dna dna = new Dna();
        dna.setDna(asList(
                "CTGAGA", // vertical C (top to bottom)
                "CAGAAC",
                "CTTTGT", // vertical G (top to bottom)
                "CAACGG",
                "GCCGGA",
                "TCACGG"
        ));

        // when
        DnaCategory category = categorizer.categorize(dna);

        // then
        assertThat(category, is(SIMIAN));
    }


    @Test
    public void givenAMatrixWithAtLeast1DiagonalTopToLeftSequence_andAnotherSequence_shouldReturnSimianCategory() {
        // given
        Dna dna = new Dna();
        dna.setDna(asList(
                "CGGTGA", // diagonal T (top to left)
                "CTTAAC",
                "ATCCGT",  // vertical G (top to bottom)
                "TAATGG",
                "GCGCGA",
                "TCACGG"
        ));

        // when
        DnaCategory category = categorizer.categorize(dna);

        // then
        assertThat(category, is(SIMIAN));
    }

    @Test
    public void givenAMatrixWithAtLeast1DiagonalRightToBottomSequence_andAnotherSequence_shouldReturnSimianCategory() {
        // given
        Dna dna = new Dna();
        dna.setDna(asList(
                "CAGAGA", // diagonal G (top to left)
                "CATGAG",
                "ACGTCC", // diagonal C (right to bottom)
                "AGTCTG",
                "CGCCTG",
                "ACACGG"
        ));

        // when
        DnaCategory category = categorizer.categorize(dna);

        // then
        assertThat(category, is(SIMIAN));
    }


    @Test
    public void givenAMatrixWithAtLeast1DiagonalTopToRightSequence_andAnotherSequence_shouldReturnSimianCategory() {
        // given
        Dna dna = new Dna();
        dna.setDna(asList(
                "CTGAGA",
                "CTGAAC", // diagonal T (top to right)
                "ATTTGT",
                "AAATGG",
                "CCCCTA", // horizontal C
                "TCACGG"
        ));

        // when
        DnaCategory category = categorizer.categorize(dna);

        // then
        assertThat(category, is(SIMIAN));
    }


    @Test
    public void givenAMatrixWithAtLeast1DiagonalLeftToBottomSequence_andAnotherSequence_shouldReturnSimianCategory() {
        // given
        Dna dna = new Dna();
        dna.setDna(asList(
                "TTGAGA", // diagonal T (top to right)
                "CTGAAC",
                "ATTTGT", // diagonal A (left to bottom)
                "AAATGG",
                "CCACAA",
                "TCAAGG"
        ));

        // when
        DnaCategory category = categorizer.categorize(dna);

        // then
        assertThat(category, is(SIMIAN));
    }

    @Test
    public void givenAMatrixWith2SubsequentSequences_shouldReturnSimianCategory() {
        // given
        Dna dna = new Dna();
        dna.setDna(asList(
                "TTGAGATC", // diagonal T (top to right)
                "CTGAACCT",
                "ATAGTCGA", // diagonal A (left to bottom)
                "AATTCGAT",
                "CTACAATC",
                "GCCTGGCA",
                "TCAAGAGG",
                "CCACTCGT"
        ));

        // when
        DnaCategory category = categorizer.categorize(dna);

        // then
        assertThat(category, is(SIMIAN));
    }




}