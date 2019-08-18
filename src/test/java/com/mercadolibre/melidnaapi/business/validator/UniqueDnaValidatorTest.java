package com.mercadolibre.melidnaapi.business.validator;

import com.mercadolibre.melidnaapi.model.exception.BusinessException;
import com.mercadolibre.melidnaapi.model.table.Dna;
import com.mercadolibre.melidnaapi.repository.DnaRepository;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UniqueDnaValidatorTest {

    @InjectMocks
    private UniqueDnaValidator validator;

    @Mock
    private DnaRepository repository;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test(expected = Test.None.class /* no exception expected */)
    public void givenANewDna_shouldAcceptDna() {
        // given
        Dna dna = new Dna();
        dna.setDnaId("test-id");

        when(repository.existsById("test-id")).thenReturn(false);

        // when
        validator.validate(dna);

        // then should not thrown exception
    }

    @Test
    public void givenAnExistingDna_shouldThrowException() {
        // given
        Dna dna = new Dna();
        dna.setDnaId("test-id");

        when(repository.existsById("test-id")).thenReturn(true);

        // should
        expectedException.expect(BusinessException.class);
        expectedException.expectMessage("This DNA already exists");

        // when
        validator.validate(dna);
    }

}