package com.mercadolibre.melidnaapi.business.generator;

import com.mercadolibre.melidnaapi.business.validator.DnaValidator;
import com.mercadolibre.melidnaapi.business.validator.MatrixProportionValidator;
import com.mercadolibre.melidnaapi.business.validator.NitrogenousBasesValidator;
import com.mercadolibre.melidnaapi.business.validator.UniqueDnaValidator;
import com.mercadolibre.melidnaapi.model.table.Dna;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DnaGenerationPreparatorTest {

    @InjectMocks
    private DnaGenerationPreparator preparator;

    @Spy
    private List<DnaValidator> validators = new ArrayList<>();

    @Before
    public void setUp() {
        validators.add(mock(MatrixProportionValidator.class));
        validators.add(mock(NitrogenousBasesValidator.class));
        validators.add(mock(UniqueDnaValidator.class));
    }

    @Test
    public void givenADna_shouldPrepareDnaData_andRunGenericDnaValidations() {
        // given
        Dna dna = new Dna();
        dna.setDna(asList(
                "ctgaga",
                "ctgagc",
                "tattgt",
                "agaggg",
                "ccccta",
                "tcactg"
        ));

        // when
        preparator.prepare(dna);

        // then
        assertThat(dna.getDnaId(), is("d4c29ecd-4162-3e72-95b9-042c03be0edf"));
        assertThat(dna.getDna(), is(asList(
                "CTGAGA",
                "CTGAGC",
                "TATTGT",
                "AGAGGG",
                "CCCCTA",
                "TCACTG"
        )));

        // then
        assertThat(validators, hasSize(3));
        validators.forEach(validator -> verify(validator).validate(dna));
    }

}