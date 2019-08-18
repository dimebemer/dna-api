package com.mercadolibre.melidnaapi.business.validator;

import com.mercadolibre.melidnaapi.config.props.DnaProperties;
import com.mercadolibre.melidnaapi.model.exception.BusinessException;
import com.mercadolibre.melidnaapi.model.table.Dna;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MatrixProportionValidatorTest {

    @InjectMocks
    private MatrixProportionValidator validator;

    @Mock
    private DnaProperties props;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        when(props.getMinimumMatrixSize()).thenReturn(4);
    }

    @Test(expected = Test.None.class /* should not thrown any exception */)
    public void givenASquareDnaMatrixWithAtLeast4Size_shouldAccept() {
        // given
        Dna dna = new Dna();
        dna.setDna(asList(
                "AGTC",
                "GTAA",
                "GGAT",
                "AAAA"
        ));

        // when
        validator.validate(dna);

        // then should not thrown any exception
    }

    @Test
    public void givenADnaMatrixWithLessRowsThanColumns_shouldThrowException() {
        // given
        Dna dna = new Dna();
        dna.setDna(asList(
                "AGTC",
                "GTAA",
                "AAAA"
        ));

        // should
        expectedException.expect(BusinessException.class);
        expectedException.expectMessage("DNA Matrix must be square");

        // when
        validator.validate(dna);
    }

    @Test
    public void givenADnaMatrixWithLessColumnsThanRows_shouldThrowException() {
        // given
        Dna dna = new Dna();
        dna.setDna(asList(
                "AGTC",
                "GTAA",
                "GTAA",
                "GTAC",
                "AAAA"
        ));

        // should
        expectedException.expect(BusinessException.class);
        expectedException.expectMessage("DNA Matrix must be square");

        // when
        validator.validate(dna);
    }

    @Test
    public void givenADnaMatrixWithLessThanMinimumLetters_shouldThrowException() {
        // given
        Dna dna = new Dna();
        dna.setDna(asList(
                "AGT",
                "CTC",
                "GTC"
        ));

        // should
        expectedException.expect(BusinessException.class);
        expectedException.expectMessage("DNA Matrix must have at least 4 letters per row");

        // when
        validator.validate(dna);
    }

}