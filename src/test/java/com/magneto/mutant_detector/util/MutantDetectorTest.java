package com.magneto.mutant_detector.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MutantDetectorTest {

    @Test
    public void testMutantExample() {
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
    public void testNonMutant() {
        String[] dna = {
                "ATGC",
                "CAGT",
                "TTAT",
                "AGAA"
        };

        assertFalse(MutantDetector.isMutant(dna));
    }

    @Test
    public void testHorizontalSequenceOnly() {
        String[] dna = {
                "AAAAT",
                "CAGTG",
                "TTATG",
                "AGAAA",
                "TCACT"
        };

        assertTrue(MutantDetector.isMutant(dna));
    }

    @Test
    public void testVerticalSequenceOnly() {
        String[] dna = {
                "ATCGA",
                "ATCGG",
                "ATCGA",
                "ATCGT",
                "AAAAA"
        };

        assertTrue(MutantDetector.isMutant(dna));
    }

    @Test
    public void testDiagonalDescendingSequence() {
        String[] dna = {
                "A----",
                "-A---",
                "--A--",
                "---A-",
                "-----"
        };

        for (int i = 0; i < dna.length; i++) {
            dna[i] = dna[i].replace('-', 'C');
        }
        assertTrue(MutantDetector.isMutant(dna));
    }

    @Test
    public void testDiagonalAscendingSequence() {
        String[] dna = {
                "----G",
                "---G-",
                "--G--",
                "-G---",
                "G----"
        };
        for (int i = 0; i < dna.length; i++) {
            dna[i] = dna[i].replace('-', 'T');
        }
        assertTrue(MutantDetector.isMutant(dna));
    }

    @Test
    public void testOverlappingSequencesCounted() {
        String[] dna = {
                "AAAAA",
                "CCCCC",
                "TTTTT",
                "GGGGG",
                "AAAAA"
        };

        assertTrue(MutantDetector.isMutant(dna));
    }

    @Test
    public void testSingleSequenceNotMutant() {
        String[] dna = {
                "ATGC",
                "CAGT",
                "TTTT",
                "AGAA"
        };

        assertFalse(MutantDetector.isMutant(dna));
    }

    @Test
    public void testInvalidCharsThrows() {
        String[] dna = {
                "ATGX",
                "CAGT",
                "TTAT",
                "AGAA"
        };
        assertThrows(IllegalArgumentException.class, () -> MutantDetector.isMutant(dna));
    }

    @Test
    public void testNullDnaThrows() {
        assertThrows(IllegalArgumentException.class, () -> MutantDetector.isMutant(null));
    }

    @Test
    public void testNonSquareMatrixThrows() {
        String[] dna = { "ATGC", "CAG", "TTAT" };
        assertThrows(IllegalArgumentException.class, () -> MutantDetector.isMutant(dna));
    }

    @Test
    public void testMinimumSize4x4Boundary() {
        String[] dna = {
                "AAAA",
                "ACCC",
                "ATTT",
                "AGGG"
        };

        assertTrue(MutantDetector.isMutant(dna));
    }
}
