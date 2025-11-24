package com.magneto.mutant_detector.repository;

import com.magneto.mutant_detector.model.DnaRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class DnaRecordRepositoryImplIntegrationTest {

    private final DnaRecordRepository repository;

    @Autowired
    DnaRecordRepositoryImplIntegrationTest(DnaRecordRepository repository) {
        this.repository = repository;
    }

    @Test
    void testSaveNewRecordAndFindByDnaHash() {
        DnaRecord record = new DnaRecord(null, "hash-xyz", true, LocalDateTime.now());
        DnaRecord saved = repository.save(record);
        assertNotNull(saved);
        assertNotNull(saved.getId());

        DnaRecord found = repository.findByDnaHash("hash-xyz");
        assertNotNull(found);
        assertEquals(saved.getId(), found.getId());
        assertTrue(repository.existsByDnaHash("hash-xyz"));
    }

    @Test
    void testExistsByDnaHashFalseWhenNotPresent() {
        boolean exists = repository.existsByDnaHash("non-existent-hash");
        assertFalse(exists);
    }

    @Test
    void testCountByIsMutantTrueAndFalse() {

        IntStream.range(0,2).forEach(i -> repository.save(new DnaRecord(null, "m-"+i, true, LocalDateTime.now())));
        IntStream.range(0,3).forEach(i -> repository.save(new DnaRecord(null, "n-"+i, false, LocalDateTime.now())));

        long trueCount = repository.countByMutantTrue();
        long falseCount = repository.countByMutantFalse();

        assertEquals(2, trueCount);
        assertEquals(3, falseCount);
    }

    @Test
    void testFindByDnaHashReturnsNullWhenAbsent() {
        DnaRecord r = repository.findByDnaHash("absent-hash-123");
        assertNull(r);
    }
}
