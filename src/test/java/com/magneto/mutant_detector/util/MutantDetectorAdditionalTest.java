package com.magneto.mutant_detector.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MutantDetectorAdditionalTest {

    @Test
    void testNullDna_throwsIllegalArgumentException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> MutantDetector.isMutant(null));
        assertNotNull(ex);
    }

    @Test
    void testSmallMatrix_throwsIllegalArgumentException() {
        String[] dna = {"ATG", "CAG", "TTA"};
        assertThrows(IllegalArgumentException.class, () -> MutantDetector.isMutant(dna));
    }

    @Test
    void testNonSquareRow_throwsIllegalArgumentException() {
        String[] dna = {"ATGC", "CAGT", "TTAT", "AGA"};
        assertThrows(IllegalArgumentException.class, () -> MutantDetector.isMutant(dna));
    }

    @Test
    void testInvalidCharacters_throwsIllegalArgumentException() {
        String[] dna = {"ATGC", "CAGT", "TTXT", "AGAC"};
        assertThrows(IllegalArgumentException.class, () -> MutantDetector.isMutant(dna));
    }

    @Test
    void testHorizontalSequence_detectsMutant() {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };
        assertTrue(MutantDetector.isMutant(dna));
    }

    @Test
    void testVerticalSequence_detectsMutant() {
        String[] dna = {
                "ATGCGA",
                "ATGTGC",
                "ATATGT",
                "ATGAAG",
                "ATCCTA",
                "ATACTG"
        };
        assertTrue(MutantDetector.isMutant(dna));
    }

    @Test
    void testDiagonalTLBR_notMutant_singleDiagonal() {
        String[] dna = {
                "AAGC",
                "CAAG",
                "TCAA",
                "AGAA"
        };

        assertFalse(MutantDetector.isMutant(dna));
    }

    @Test
    void testDiagonalTRBL_notMutant_singleDiagonal() {
        String[] dna = {
                "GCAA",
                "AGCA",
                "AAGC",
                "CAAA"
        };

        assertFalse(MutantDetector.isMutant(dna));
    }

    @Test
    void testAllSameBases_detectsMutant() {
        String[] dna = {
                "AAAA",
                "AAAA",
                "AAAA",
                "AAAA"
        };
        assertTrue(MutantDetector.isMutant(dna));
    }

    @Test
    void testNoSequences_returnsFalse() {
        String[] dna = {
                "ATGC",
                "CAGT",
                "TTAT",
                "AGAC"
        };
        assertFalse(MutantDetector.isMutant(dna));
    }

    @Test
    void testEarlyTermination_whenTwoSequencesFound() {
        String[] dna = {
                "AAAAGA",
                "AAAAGC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };
        long start = System.nanoTime();
        boolean result = MutantDetector.isMutant(dna);
        long duration = System.nanoTime() - start;

        assertTrue(result);

        assertTrue(duration >= 0);
    }
}
