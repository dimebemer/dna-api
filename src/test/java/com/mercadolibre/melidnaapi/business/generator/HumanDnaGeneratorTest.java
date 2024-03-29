package com.mercadolibre.melidnaapi.business.generator;

import com.mercadolibre.melidnaapi.business.service.DnaService;
import com.mercadolibre.melidnaapi.business.validator.HumanDnaValidator;
import com.mercadolibre.melidnaapi.business.validator.IsHumanValidator;
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

import static com.mercadolibre.melidnaapi.model.table.Dna.DnaCategory.HUMAN;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class HumanDnaGeneratorTest {

    @InjectMocks
    private HumanDnaGenerator generator;

    @Mock
    private DnaGenerationPreparator preparator;

    @Mock
    private DnaService service;

    @Spy
    private List<HumanDnaValidator> validators = new ArrayList<>();

    @Mock
    private IsHumanValidator humanValidator;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();


    @Before
    public void setUp() {
        validators.add(humanValidator);
    }


    @Test
    public void givenAHumanDna_shouldPrepareData_andRunHumanValidations_andCategorizeAsHuman_andCreate() {
        // given
        Dna dna = new Dna();
        dna.setDna(asList(
                "CTGAGG",
                "CTGAGC",
                "TATTGT",
                "AGAGTG",
                "CCCATA",
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
        assertThat(generated.getCategory(), is(HUMAN));

        // then
        verify(service).create(dna);
    }

    @Test
    public void givenAInvalidHumanDna_shouldNotCreate() {
        // given
        Dna dna = new Dna();
        dna.setDna(emptyList());

        doThrow(RuntimeException.class).when(humanValidator).validate(any(Dna.class));
        expectedException.expect(RuntimeException.class);

        // when
        generator.generate(dna);

        // then
        verify(service, never()).create(any(Dna.class));
    }

}