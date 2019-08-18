package com.mercadolibre.melidnaapi.business.generator;

import com.mercadolibre.melidnaapi.business.service.DnaService;
import com.mercadolibre.melidnaapi.business.validator.IsSimianValidator;
import com.mercadolibre.melidnaapi.business.validator.SimianDnaValidator;
import com.mercadolibre.melidnaapi.model.table.Dna;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static com.mercadolibre.melidnaapi.model.table.Dna.DnaCategory.SIMIAN;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SimianDnaGeneratorTest {

    @InjectMocks
    private SimianDnaGenerator generator;

    @Mock
    private DnaGenerationPreparator preparator;

    @Mock
    private DnaService service;

    @Spy
    private List<SimianDnaValidator> validators = new ArrayList<>();

    @Mock
    private IsSimianValidator simianValidator;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();


    @Before
    public void setUp() {
        validators.add(simianValidator);
    }


    @Test
    public void givenASimianDna_shouldPrepareData_andRunSimianValidations_andCategorizeAsSimian_andCreate() {
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

        when(service.create(dna)).thenReturn(dna);

        // when
        Dna generated = generator.generate(dna);

        // then
        verify(preparator).prepare(dna);

        // then
        assertThat(validators, hasSize(1));
        validators.forEach(validator -> verify(validator).validate(dna));

        // then
        assertThat(generated.getCategory(), is(SIMIAN));

        // then
        verify(service).create(dna);
    }

    @Test
    public void givenAInvalidSimianDna_shouldNotCreate() {
        // given
        Dna dna = new Dna();
        dna.setDna(emptyList());

        doThrow(RuntimeException.class).when(simianValidator).validate(any(Dna.class));
        expectedException.expect(RuntimeException.class);

        // when
        generator.generate(dna);

        // then
        verify(service, never()).create(any(Dna.class));
    }

}