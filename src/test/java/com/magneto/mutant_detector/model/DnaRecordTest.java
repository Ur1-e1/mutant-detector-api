package com.magneto.mutant_detector.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class DnaRecordTest {

    @Test
    void testCreatedAtIsSetOnNoArgs() {
        DnaRecord record = new DnaRecord();
        assertNotNull(record.getCreatedAt(), "createdAt should be initialized");
        LocalDateTime now = LocalDateTime.now();
        // createdAt should not be in the future
        assertFalse(record.getCreatedAt().isAfter(now.plusSeconds(1)), "createdAt should be <= now");
    }

    @Test
    void testAllArgsConstructorAndGetters() {
        LocalDateTime dt = LocalDateTime.of(2020, 1, 1, 0, 0);
        DnaRecord record = new DnaRecord(1L, "hash123", true, dt);
        assertEquals(1L, record.getId());
        assertEquals("hash123", record.getDnaHash());
        assertTrue(record.isMutant());
        assertEquals(dt, record.getCreatedAt());
    }

    @Test
    void testJPAAnnotationsPresent() {
        Class<DnaRecord> clazz = DnaRecord.class;
        boolean hasEntity = clazz.isAnnotationPresent(jakarta.persistence.Entity.class);
        boolean hasTable = clazz.isAnnotationPresent(jakarta.persistence.Table.class);
        assertTrue(hasEntity, "DnaRecord should have @Entity");
        assertTrue(hasTable, "DnaRecord should have @Table");
    }

    @Test
    public void testGettersAndSetters() {
        DnaRecord r = new DnaRecord();
        r.setDnaHash("abc123");
        r.setMutant(true);
        r.setId(10L);
        r.setCreatedAt(LocalDateTime.of(2020,1,1,0,0));

        assertEquals(10L, r.getId());
        assertEquals("abc123", r.getDnaHash());
        assertTrue(r.isMutant());
        assertEquals(LocalDateTime.of(2020,1,1,0,0), r.getCreatedAt());
    }
}
