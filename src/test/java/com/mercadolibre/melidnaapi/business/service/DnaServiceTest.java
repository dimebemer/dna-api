package com.mercadolibre.melidnaapi.business.service;

import com.mercadolibre.melidnaapi.model.exception.NotFoundException;
import com.mercadolibre.melidnaapi.model.table.Dna;
import com.mercadolibre.melidnaapi.repository.DnaRepository;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DnaServiceTest {

    @InjectMocks
    private DnaService service;

    @Mock
    private DnaRepository repository;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void givenADna_shouldCreateInDatabase() {
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

        dna.generateId();
        dna.categorizeAsSimian();

        // when
        service.create(dna);

        // then
        verify(repository).save(dna);
    }

    @Test
    public void givenAnExistingDnaId_shouldReturnDna() {
        // given
        Dna dna = new Dna();
        final String id = "d4c29ecd-4162-3e72-95b9-042c03be0edf";

        when(repository.findById(id)).thenReturn(Optional.of(dna));

        // when
        Dna returned = service.find(id);

        // then
        assertThat(returned, sameInstance(dna) );
    }

    @Test
    public void givenAnInexistentDnaId_shouldThrowException() {
        // given
        final String id = "abcdefg-4162-3e72-95b9-042c03be0edd";

        when(repository.findById(id)).thenReturn(Optional.empty());

        // should
        expectedException.expect(NotFoundException.class);

        // when
        service.find(id);
    }

}