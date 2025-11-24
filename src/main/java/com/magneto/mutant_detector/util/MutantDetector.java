package com.magneto.mutant_detector.util;

public class MutantDetector {

    public static boolean isMutant(String[] dna) {
        if (dna == null) {
            throw new IllegalArgumentException("dna must not be null");
        }

        int n = dna.length;
        if (n < 4) {
            throw new IllegalArgumentException("dna must be an NxN matrix and at least 4x4");
        }

        // Convertir a matriz de caracteres
        char[][] m = new char[n][n];
        for (int i = 0; i < n; i++) {
            String row = dna[i];
            if (row == null || row.length() != n) {
                throw new IllegalArgumentException("dna must be an NxN matrix");
            }
            for (int j = 0; j < n; j++) {
                char c = row.charAt(j);
                if (c >= 'a' && c <= 'z') c = (char) (c - 'a' + 'A');
                if (c != 'A' && c != 'T' && c != 'C' && c != 'G') {
                    throw new IllegalArgumentException("dna contains invalid characters");
                }
                m[i][j] = c;
            }
        }

        int sequencesFound = 0;
        final int SEQ_LEN = 4;

        // Horizontal
        for (int i = 0; i < n; i++) {
            int consec = 1;
            for (int j = 1; j < n; j++) {
                if (m[i][j] == m[i][j - 1]) {
                    consec++;
                    if (consec >= SEQ_LEN) {
                        sequencesFound++;
                        if (sequencesFound > 1) return true;
                    }
                } else {
                    consec = 1;
                }
            }
        }

        // Vertical
        for (int j = 0; j < n; j++) {
            int consec = 1;
            for (int i = 1; i < n; i++) {
                if (m[i][j] == m[i - 1][j]) {
                    consec++;
                    if (consec >= SEQ_LEN) {
                        sequencesFound++;
                        if (sequencesFound > 1) return true;
                    }
                } else {
                    consec = 1;
                }
            }
        }

        // Diagonales
        for (int colStart = 0; colStart <= n - SEQ_LEN; colStart++) {
            int len = Math.min(n - colStart, n);
            if (len < SEQ_LEN) continue;
            int consec = 1;
            for (int k = 1; k < len; k++) {
                if (m[k][colStart + k] == m[k - 1][colStart + k - 1]) {
                    consec++;
                    if (consec >= SEQ_LEN) {
                        sequencesFound++;
                        if (sequencesFound > 1) return true;
                    }
                } else {
                    consec = 1;
                }
            }
        }
        // Empezar por primera columna
        for (int rowStart = 1; rowStart <= n - SEQ_LEN; rowStart++) {
            int len = Math.min(n - rowStart, n);
            if (len < SEQ_LEN) continue;
            int consec = 1;
            for (int k = 1; k < len; k++) {
                if (m[rowStart + k][k] == m[rowStart + k - 1][k - 1]) {
                    consec++;
                    if (consec >= SEQ_LEN) {
                        sequencesFound++;
                        if (sequencesFound > 1) return true;
                    }
                } else {
                    consec = 1;
                }
            }
        }

        // Diagonales inversas
        // Empezar por primera fila
        for (int colStart = SEQ_LEN - 1; colStart < n; colStart++) {
            int len = Math.min(colStart + 1, n);
            if (len < SEQ_LEN) continue;
            int consec = 1;
            for (int k = 1; k < len; k++) {
                int i = k;
                int j = colStart - k;
                if (m[i][j] == m[i - 1][j + 1]) {
                    consec++;
                    if (consec >= SEQ_LEN) {
                        sequencesFound++;
                        if (sequencesFound > 1) return true;
                    }
                } else {
                    consec = 1;
                }
            }
        }
        // Empezar por última columna
        for (int rowStart = 1; rowStart <= n - SEQ_LEN; rowStart++) {
            int len = Math.min(n - rowStart, n);
            if (len < SEQ_LEN) continue;
            int consec = 1;
            for (int k = 1; k < len; k++) {
                int i = rowStart + k;
                int j = n - 1 - k;
                if (m[i][j] == m[i - 1][j + 1]) {
                    consec++;
                    if (consec >= SEQ_LEN) {
                        sequencesFound++;
                        if (sequencesFound > 1) return true;
                    }
                } else {
                    consec = 1;
                }
            }
        }

        return false;
    }
}
