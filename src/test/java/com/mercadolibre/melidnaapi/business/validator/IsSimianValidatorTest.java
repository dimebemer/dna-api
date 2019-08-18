package com.mercadolibre.melidnaapi.business.validator;

import com.mercadolibre.melidnaapi.business.categorizer.DnaCategorizer;
import com.mercadolibre.melidnaapi.model.exception.NotAllowedException;
import com.mercadolibre.melidnaapi.model.table.Dna;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static com.mercadolibre.melidnaapi.model.table.Dna.DnaCategory.HUMAN;
import static com.mercadolibre.melidnaapi.model.table.Dna.DnaCategory.SIMIAN;
import static java.util.Collections.emptyList;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class IsSimianValidatorTest {

    @InjectMocks
    private IsSimianValidator validator;

    @Mock
    private DnaCategorizer categorizer;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test(expected = Test.None.class /* no exception expected */)
    public void givenASimianDna_shouldAcceptDna() {
        // given
        Dna dna = new Dna();
        dna.setDna(emptyList());

        when(categorizer.categorize(dna)).thenReturn(SIMIAN);

        // when
        validator.validate(dna);

        // then should not throw exception
    }

    @Test
    public void givenANonSimianDna_shouldThrowException() {
        // given
        Dna dna = new Dna();
        dna.setDna(emptyList());

        when(categorizer.categorize(dna)).thenReturn(HUMAN);

        // should
        expectedException.expect(NotAllowedException.class);
        expectedException.expectMessage("DNA is not of a Simian");

        // when
        validator.validate(dna);
    }

}