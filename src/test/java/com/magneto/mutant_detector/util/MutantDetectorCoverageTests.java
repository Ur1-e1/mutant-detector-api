package com.magneto.mutant_detector.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MutantDetectorCoverageTests {

    @Test
    void testLowerCaseInput_detectsMutant() {
        String[] dna = {
                "aaaaga",
                "AAAAGC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };

        assertTrue(MutantDetector.isMutant(dna));
    }

    @Test
    void testTLBR_diagonal_startingAtColumn_detectsMutant() {

        String[] dna = {
                "AATAAA",
                "CGAAGA",
                "TGTAAG",
                "AGACAA",
                "CCGCTA",
                "TCACTG"
        };

        dna[5] = "TTTTTG";
        assertTrue(MutantDetector.isMutant(dna));
    }

    @Test
    void testTLBR_diagonal_startingAtRow_detectsMutant() {

        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "AATGGT",
                "CAAAGG",
                "CCTCTA",
                "TCAATG"
        };

        dna[0] = "AAAAAA";
        assertTrue(MutantDetector.isMutant(dna));
    }

    @Test
    void testTRBL_diagonal_startingAtMiddleColumn_detectsMutant() {

        String[] dna = {
                "ATGCAA",
                "CAGAGA",
                "TTAAGT",
                "AGAAGG",
                "CCGCTA",
                "TCACTG"
        };

        dna[0] = "ATGCAA";
        dna[1] = "CAGAAG";
        dna[2] = "TTAAAT";
        dna[3] = "AGAAGG";

        dna[4] = "CCCCAA";
        dna[3] = "AAAAGG";
        assertTrue(MutantDetector.isMutant(dna));
    }

    @Test
    void testTRBL_diagonal_startingAtRow_detectsMutant() {

        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATGG",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };

        dna[2] = "TTATCC";
        dna[3] = "AGAACC";
        dna[4] = "CCCCTA";
        dna[5] = "TCACCG";

        dna[0] = "GGGGGA";
        assertTrue(MutantDetector.isMutant(dna));
    }

    @Test
    void testOverlappingSequences_countedAndEarlyTermination() {

        String[] dna = {
                "AAAAAA",
                "AAAAAA",
                "TTTTTT",
                "GGGGGG",
                "CCCCCC",
                "ATCATT"
        };

        assertTrue(MutantDetector.isMutant(dna));
    }
}
