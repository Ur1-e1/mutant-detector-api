package com.magneto.mutant_detector.service;

import com.magneto.mutant_detector.dto.StatsResponse;
import com.magneto.mutant_detector.model.DnaRecord;
import com.magneto.mutant_detector.repository.DnaRecordRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class MutantServiceIntegrationTest {

    @Autowired
    private MutantService mutantService;

    @Autowired
    private StatsService statsService;

    @Autowired
    private DnaRecordRepository dnaRecordRepository;

    @Test
    public void testPersistAndStats() {
        String[] mutantDna = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };

        String[] humanDna = {
                "ATGC",
                "CAGT",
                "TTAT",
                "AGAA"
        };

        boolean m = mutantService.isMutant(mutantDna);
        assertTrue(m);

        boolean h = mutantService.isMutant(humanDna);
        assertFalse(h);

        StatsResponse stats = statsService.getStats();
        assertEquals(1L, stats.countMutantDna());
        assertEquals(1L, stats.countHumanDna());
        assertEquals(1.0, stats.ratio());
    }
}
