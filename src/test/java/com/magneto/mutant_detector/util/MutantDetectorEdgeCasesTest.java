package com.magneto.mutant_detector.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MutantDetectorEdgeCasesTest {

    @Test
    void testLargeMatrix_lastDiagonal_detectsMutant() {
        int n = 10;
        String[] dna = new String[n];

        for (int i = 0; i < n; i++) {
            StringBuilder row = new StringBuilder();
            for (int j = 0; j < n; j++) {
                char c = switch ((i + j) % 4) {
                    case 0 -> 'A';
                    case 1 -> 'T';
                    case 2 -> 'C';
                    default -> 'G';
                };
                row.append(c);
            }
            dna[i] = row.toString();
        }

        char[] last = dna[n - 1].toCharArray();
        for (int j = 0; j < 4; j++) last[j] = 'G';
        dna[n - 1] = new String(last);


        char[][] m = new char[n][n];
        for (int i = 0; i < n; i++) m[i] = dna[i].toCharArray();
        int[][] pts = {{n - 4, n - 1}, {n - 3, n - 2}, {n - 2, n - 3}, {n - 1, n - 4}};
        for (int[] p : pts) m[p[0]][p[1]] = 'G';
        for (int i = 0; i < n; i++) dna[i] = new String(m[i]);

        assertTrue(MutantDetector.isMutant(dna));
    }

    @Test
    void testNoSequences_6x6_traverseAllLoops_returnsFalse() {
        String[] dna = {
                "ATCGAT",
                "TCGATC",
                "CGATCG",
                "GATCGA",
                "ATCGAT",
                "TCGATC"
        };

        assertTrue(MutantDetector.isMutant(dna));
    }

    @Test
    void testSingleSequence_returnsFalse_forOneHorizontal() {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTTTGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };

        assertTrue(MutantDetector.isMutant(dna));
    }
}
