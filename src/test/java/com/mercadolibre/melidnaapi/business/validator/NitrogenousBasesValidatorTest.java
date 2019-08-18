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
public class NitrogenousBasesValidatorTest {

    @InjectMocks
    private NitrogenousBasesValidator validator;

    @Mock
    private DnaProperties props;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        when(props.getNitrogenousBases()).thenReturn(new char[]{'A', 'T', 'C', 'G'});
    }

    @Test(expected = Test.None.class /* no exception thrown */)
    public void givenADnaWithValidNitrogenousBases_shouldAccept() {
        // given
        Dna dna = new Dna();
        dna.setDna(asList(
                "CTGAGA",
                "CTGAGC",
                "TATTGT",
                "AGAGGG",
                "CCCCTA",
                "TCACTG"
        ));

        // when
        validator.validate(dna);

        // then should not thrown any exception
    }

    @Test
    public void givenADnaWithInvalidNitrogenousBases_shouldThrowException() {
        // given
        Dna dna = new Dna();
        dna.setDna(asList(
                "CTGAGA",
                "CTGAGC",
                "TATTGT",
                "AGDGGG",
                "CCCCTA",
                "TCACTG"
        ));

        // should
        expectedException.expect(BusinessException.class);
        expectedException.expectMessage("DNA should only contain letters [A, T, C, G]");

        // when
        validator.validate(dna);
    }

}