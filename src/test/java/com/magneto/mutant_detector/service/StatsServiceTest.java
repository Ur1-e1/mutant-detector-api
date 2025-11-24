package com.magneto.mutant_detector.service;

import com.magneto.mutant_detector.dto.StatsResponse;
import com.magneto.mutant_detector.repository.DnaRecordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StatsServiceTest {

    private DnaRecordRepository repository;
    private StatsService statsService;

    @BeforeEach
    public void setup() {
        repository = mock(DnaRecordRepository.class);
        statsService = new StatsService(repository);
    }

    @Test
    public void whenNoRecords_ratioIsZero() {
        when(repository.countByMutantTrue()).thenReturn(0L);
        when(repository.countByMutantFalse()).thenReturn(0L);

        StatsResponse stats = statsService.getStats();
        assertEquals(0L, stats.countMutantDna());
        assertEquals(0L, stats.countHumanDna());
        assertEquals(0.0, stats.ratio());
    }

    @Test
    public void whenSomeRecords_ratioCalculated() {
        when(repository.countByMutantTrue()).thenReturn(2L);
        when(repository.countByMutantFalse()).thenReturn(4L);

        StatsResponse stats = statsService.getStats();
        assertEquals(2L, stats.countMutantDna());
        assertEquals(4L, stats.countHumanDna());
        assertEquals(0.5, stats.ratio());
    }
}
