package com.magneto.mutant_detector.service;

import com.magneto.mutant_detector.model.DnaRecord;
import com.magneto.mutant_detector.repository.DnaRecordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MutantServiceTest {

    private DnaRecordRepository repository;
    private MutantService service;

    @BeforeEach
    public void setup() {
        repository = Mockito.mock(DnaRecordRepository.class);
        service = new MutantService(repository);
    }

    @Test
    public void whenDnaAlreadyExists_thenReturnStoredValueAndDoNotSave() {
        String[] dna = {"ATGC", "CAGT", "TTAT", "AGAA"};
        String hash = String.join("|", dna);
        DnaRecord stored = new DnaRecord();
        stored.setDnaHash(hash);
        stored.setMutant(true);

        when(repository.existsByDnaHash(anyString())).thenReturn(true);
        when(repository.findByDnaHash(anyString())).thenReturn(stored);

        boolean result = service.isMutant(dna);
        assertTrue(result);
        verify(repository, never()).save(any());
    }

    @Test
    public void whenNewMutantDna_thenSaveRecordWithMutantTrue() {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };

        when(repository.existsByDnaHash(anyString())).thenReturn(false);
        when(repository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        boolean result = service.isMutant(dna);
        assertTrue(result);

        ArgumentCaptor<DnaRecord> captor = ArgumentCaptor.forClass(DnaRecord.class);
        verify(repository, times(1)).save(captor.capture());
        DnaRecord saved = captor.getValue();
        assertTrue(saved.isMutant());
        assertNotNull(saved.getDnaHash());
    }

    @Test
    public void whenNewHumanDna_thenSaveRecordWithMutantFalse() {
        String[] dna = {"ATGC", "CAGT", "TTAT", "AGAA"};
        when(repository.existsByDnaHash(anyString())).thenReturn(false);
        when(repository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        boolean result = service.isMutant(dna);
        assertFalse(result);

        ArgumentCaptor<DnaRecord> captor = ArgumentCaptor.forClass(DnaRecord.class);
        verify(repository, times(1)).save(captor.capture());
        DnaRecord saved = captor.getValue();
        assertFalse(saved.isMutant());
    }
}

