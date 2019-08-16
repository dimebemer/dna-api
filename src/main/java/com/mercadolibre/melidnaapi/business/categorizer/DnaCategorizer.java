package com.mercadolibre.melidnaapi.business.categorizer;

import com.mercadolibre.melidnaapi.model.table.Dna;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DnaCategorizer {

    private static final int SEQUENCE_SIZE = 4;
    private static final int MINIMUM_SIMIAN_SEQUENCES = 2;

    public void categorize(Dna dna) {
        if (isSimian(dna.getDna())) {
            dna.categorizeAsSimian();
        } else {
            dna.categorizeAsHuman();
        }
    }

    private boolean isSimian(List<String> dna) {
        char[][] dnaMatrix = fetchDnaMatrix(dna);

        final int base = dna.size();

        int total4LetterSequences = 0;
        int seqLetters = 1;

        // Check horizontally
        for (int row = 0; row < base; row++) {
            int col = 0;

            while (col < base - SEQUENCE_SIZE + seqLetters) {
                if (dnaMatrix[row][col] == dnaMatrix[row][col+1]) {
                    seqLetters++;

                    if (seqLetters == SEQUENCE_SIZE) {
                        total4LetterSequences++;

                        if (total4LetterSequences >= MINIMUM_SIMIAN_SEQUENCES) {
                            return true;
                        }

                        seqLetters = 1;
                    }
                } else {
                    seqLetters = 1;
                }

                col++;
            }

            seqLetters = 1;
        }

        // Check vertically
        for (int col = 0; col < base; col++) {
            int row = 0;

            while (row < base - SEQUENCE_SIZE + seqLetters) {
                if (dnaMatrix[row][col] == dnaMatrix[row+1][col]) {
                    seqLetters++;

                    if (seqLetters == SEQUENCE_SIZE) {
                        total4LetterSequences++;

                        if (total4LetterSequences >= MINIMUM_SIMIAN_SEQUENCES) {
                            return true;
                        }

                        seqLetters = 1;
                    }
                } else {
                    seqLetters = 1;
                }

                row++;
            }

            seqLetters = 1;
        }

        // top to left diagonals
        for (int baseCol = SEQUENCE_SIZE - 1; baseCol < base; baseCol++) {

            for (int row=0, col=baseCol; col >= SEQUENCE_SIZE - seqLetters; row++, col--) {
                if (dnaMatrix[row][col] == dnaMatrix[row+1][col-1]) {
                    seqLetters++;

                    if (seqLetters == SEQUENCE_SIZE) {
                        total4LetterSequences++;

                        if (total4LetterSequences >= MINIMUM_SIMIAN_SEQUENCES) {
                            return true;
                        }

                        seqLetters = 1;
                    }
                } else {
                    seqLetters = 1;
                }
            }

            seqLetters = 1;
        }

        // right to bottom diagonals
        for (int baseRow = base - SEQUENCE_SIZE; baseRow > 0; baseRow--) { // 2

            for (int row=baseRow, col=base-1; row < base - SEQUENCE_SIZE + seqLetters; row++, col--) { // 3, 4
                if (dnaMatrix[row][col] == dnaMatrix[row + 1][col - 1]) {
                    seqLetters++;

                    if (seqLetters == SEQUENCE_SIZE) {
                        total4LetterSequences++;

                        if (total4LetterSequences >= MINIMUM_SIMIAN_SEQUENCES) {
                            return true;
                        }

                        seqLetters = 1;
                    }
                } else {
                    seqLetters = 1;
                }
            }

            seqLetters = 1;
        }

        // top to right diagonals
        for (int baseCol = base - SEQUENCE_SIZE; baseCol > 0; baseCol--) {

            for (int row=0, col=baseCol; col < base - SEQUENCE_SIZE + seqLetters; row++, col++) {
                if (dnaMatrix[row][col] == dnaMatrix[row+1][col+1]) {
                    seqLetters++;

                    if (seqLetters == SEQUENCE_SIZE) {
                        total4LetterSequences++;

                        if (total4LetterSequences >= MINIMUM_SIMIAN_SEQUENCES) {
                            return true;
                        }

                        seqLetters = 1;
                    }
                } else {
                    seqLetters = 1;
                }
            }

            seqLetters = 1;
        }

        // left to bottom diagonals
        for (int baseRow = base - SEQUENCE_SIZE; baseRow > 0; baseRow--) {

            for (int row = baseRow, col = 0; row < base - SEQUENCE_SIZE + seqLetters; row++, col++) {
                if (dnaMatrix[row][col] == dnaMatrix[row + 1][col + 1]) {
                    seqLetters++;

                    if (seqLetters == SEQUENCE_SIZE) {
                        total4LetterSequences++;

                        if (total4LetterSequences >= MINIMUM_SIMIAN_SEQUENCES) {
                            return true;
                        }

                        seqLetters = 1;
                    }
                } else {
                    seqLetters = 1;
                }
            }

            seqLetters = 1;
        }

        return false;
    }

    private char[][] fetchDnaMatrix(List<String> dna) {
        final int base = dna.size();

        char[][] dnaMatrix = new char[base][base];

        for (int i = 0; i < base; i++) {
            String row = dna.get(i);
            for (int j = 0; j < base; j++) {

                dnaMatrix[i][j] = row.charAt(j);
            }
        }

        return dnaMatrix;
    }
//
//    public static void main(String[] args) {
//        List<String> dna = Arrays.asList(
//                "CTGAAA",
//                "CTAGGC",
//                "TAGTGT",
//                "AGAGTA",
//                "CCCGTA",
//                "TCACTG"
//        );
//
//        long start = System.currentTimeMillis();
//
//        System.out.println(new DnaCategorizer().isSimian(dna));
//
//        System.out.println(System.currentTimeMillis() - start);
//    }

}
