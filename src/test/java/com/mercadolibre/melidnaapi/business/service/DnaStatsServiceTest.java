package com.mercadolibre.melidnaapi.business.service;

import com.mercadolibre.melidnaapi.model.exception.NotFoundException;
import com.mercadolibre.melidnaapi.model.table.DnaStats;
import com.mercadolibre.melidnaapi.repository.DnaStatsRepository;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DnaStatsServiceTest {

    @InjectMocks
    private DnaStatsService service;

    @Mock
    private DnaStatsRepository repository;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void givenAnExistingStatsId_shouldReturnStats() {
        // given
        DnaStats stats = new DnaStats();

        when(repository.findById("latest")).thenReturn(Optional.of(stats));

        // when
        DnaStats returned = service.findLatest();

        // then
        assertThat(returned, sameInstance(stats));
    }

    @Test
    public void givenAnInexistentStatsId_shouldThrowException() {
        // given
        when(repository.findById("latest")).thenReturn(Optional.empty());

        // then
        expectedException.expect(NotFoundException.class);

        // when
        service.findLatest();
    }

}