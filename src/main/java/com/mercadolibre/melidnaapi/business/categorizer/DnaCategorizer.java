package com.mercadolibre.melidnaapi.business.categorizer;

import com.mercadolibre.melidnaapi.model.table.Dna;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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
        int horizontalSeq = 1;
        int verticalSeq = 1;
        int diagonalSeq = 1;

        // Check horizontally
        for (int row = 0; row < base; row++) {
            int col = 1;

            while (col <= base - SEQUENCE_SIZE + horizontalSeq) {
                if (dnaMatrix[row][col] == dnaMatrix[row][col-1]) {
                    horizontalSeq++;

                    if (horizontalSeq == SEQUENCE_SIZE) {
                        total4LetterSequences++;

                        if (total4LetterSequences >= MINIMUM_SIMIAN_SEQUENCES) {
                            return true;
                        }

                        horizontalSeq = 1;
                    }
                } else {
                    horizontalSeq = 1;
                }

                col++;
            }

            horizontalSeq = 1;
        }

        // Check vertically
        for (int col = 0; col < base; col++) {
            int row = 1;

            while (row <= base - SEQUENCE_SIZE + verticalSeq) {
                if (dnaMatrix[row][col] == dnaMatrix[row-1][col]) {
                    verticalSeq++;

                    if (verticalSeq == SEQUENCE_SIZE) {
                        total4LetterSequences++;

                        if (total4LetterSequences >= MINIMUM_SIMIAN_SEQUENCES) {
                            return true;
                        }

                        verticalSeq = 1;
                    }
                } else {
                    verticalSeq = 1;
                }

                row++;
            }

            verticalSeq = 1;
        }

        // top to left diagonals
        for (int baseCol = SEQUENCE_SIZE - 1; baseCol < base; baseCol++) {

            for (int row=1, col=baseCol-1; col >= 0; row++, col--) {
                if (dnaMatrix[row][col] == dnaMatrix[row-1][col+1]) {
                    diagonalSeq++;

                    if (diagonalSeq == SEQUENCE_SIZE) {
                        total4LetterSequences++;

                        if (total4LetterSequences >= MINIMUM_SIMIAN_SEQUENCES) {
                            return true;
                        }

                        diagonalSeq = 1;
                    }
                } else {
                    diagonalSeq = 1;
                }
            }

            diagonalSeq = 1;

        }

        // right to bottom diagonals
        for (int baseRow = base - SEQUENCE_SIZE; baseRow > 0; baseRow--) { // 2

            for (int row = baseRow + 1, col = base - 2; row < base; row++, col--) { // 3, 4
                if (dnaMatrix[row][col] == dnaMatrix[row - 1][col + 1]) {
                    diagonalSeq++;

                    if (diagonalSeq == SEQUENCE_SIZE) {
                        total4LetterSequences++;

                        if (total4LetterSequences >= MINIMUM_SIMIAN_SEQUENCES) {
                            return true;
                        }

                        diagonalSeq = 1;
                    }
                } else {
                    diagonalSeq = 1;
                }
            }

            diagonalSeq = 1;

        }

        // top to right diagonals
        for (int baseCol = base - SEQUENCE_SIZE; baseCol > 0; baseCol--) {

            for (int row=1, col=baseCol+1; col < base; row++, col++) {
                if (dnaMatrix[row][col] == dnaMatrix[row-1][col-1]) {
                    diagonalSeq++;

                    if (diagonalSeq == SEQUENCE_SIZE) {
                        total4LetterSequences++;

                        if (total4LetterSequences >= MINIMUM_SIMIAN_SEQUENCES) {
                            return true;
                        }

                        diagonalSeq = 1;
                    }
                } else {
                    diagonalSeq = 1;
                }
            }

            diagonalSeq = 1;

        }

        // left to bottom diagonals
        for (int baseRow = base - SEQUENCE_SIZE; baseRow > 0; baseRow--) {

            for (int row = baseRow + 1, col = 1; row < base; row++, col++) {
                if (dnaMatrix[row][col] == dnaMatrix[row - 1][col - 1]) {
                    diagonalSeq++;

                    if (diagonalSeq == SEQUENCE_SIZE) {
                        total4LetterSequences++;

                        if (total4LetterSequences >= MINIMUM_SIMIAN_SEQUENCES) {
                            return true;
                        }

                        diagonalSeq = 1;
                    }
                } else {
                    diagonalSeq = 1;
                }
            }

            diagonalSeq = 1;

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

}
