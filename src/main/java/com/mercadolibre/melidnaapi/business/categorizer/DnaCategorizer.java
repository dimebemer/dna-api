package com.mercadolibre.melidnaapi.business.categorizer;

import com.mercadolibre.melidnaapi.config.props.DnaProperties;
import com.mercadolibre.melidnaapi.model.table.Dna;
import com.mercadolibre.melidnaapi.model.table.Dna.DnaCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.mercadolibre.melidnaapi.model.table.Dna.DnaCategory.HUMAN;
import static com.mercadolibre.melidnaapi.model.table.Dna.DnaCategory.SIMIAN;

@Component
@RequiredArgsConstructor
public class DnaCategorizer {

    private static final int SEQUENTIAL_LETTERS_INITIAL_SIZE = 1;

    private final DnaProperties props;

    public DnaCategory categorize(Dna dna) {
        return isSimian(dna.getDna()) ? SIMIAN : HUMAN;
    }

    private boolean isSimian(List<String> dna) {
        final char[][] dnaMatrix = fetchDnaMatrix(dna);
        final int base = dna.size();

        int totalCompleteSequences = 0;
        int sequentialLetters = SEQUENTIAL_LETTERS_INITIAL_SIZE;

        // Check horizontally
        for (int row = 0; row < base; row++) {
            int col = 0;

            while (col < base - props.getSimianSequenceSize() + sequentialLetters) {
                if (dnaMatrix[row][col] == dnaMatrix[row][col+1]) {
                    sequentialLetters++;

                    if (sequentialLetters == props.getSimianSequenceSize()) {
                        totalCompleteSequences++;

                        if (totalCompleteSequences >= props.getMinimumSimianSequences()) {
                            return true;
                        }

                        sequentialLetters = SEQUENTIAL_LETTERS_INITIAL_SIZE;
                    }
                } else {
                    sequentialLetters = SEQUENTIAL_LETTERS_INITIAL_SIZE;
                }

                col++;
            }

            sequentialLetters = SEQUENTIAL_LETTERS_INITIAL_SIZE;
        }

        // Check vertically
        for (int col = 0; col < base; col++) {
            int row = 0;

            while (row < base - props.getSimianSequenceSize() + sequentialLetters) {
                if (dnaMatrix[row][col] == dnaMatrix[row+1][col]) {
                    sequentialLetters++;

                    if (sequentialLetters == props.getSimianSequenceSize()) {
                        totalCompleteSequences++;

                        if (totalCompleteSequences >= props.getMinimumSimianSequences()) {
                            return true;
                        }

                        sequentialLetters = SEQUENTIAL_LETTERS_INITIAL_SIZE;
                    }
                } else {
                    sequentialLetters = SEQUENTIAL_LETTERS_INITIAL_SIZE;
                }

                row++;
            }

            sequentialLetters = SEQUENTIAL_LETTERS_INITIAL_SIZE;
        }

        // Top to left diagonals
        for (int baseCol = props.getSimianSequenceSize() - 1; baseCol < base; baseCol++) {

            for (int row = 0, col = baseCol; col >= props.getSimianSequenceSize() - sequentialLetters; row++, col--) {
                if (dnaMatrix[row][col] == dnaMatrix[row+1][col-1]) {
                    sequentialLetters++;

                    if (sequentialLetters == props.getSimianSequenceSize()) {
                        totalCompleteSequences++;

                        if (totalCompleteSequences >= props.getMinimumSimianSequences()) {
                            return true;
                        }

                        sequentialLetters = SEQUENTIAL_LETTERS_INITIAL_SIZE;
                    }
                } else {
                    sequentialLetters = SEQUENTIAL_LETTERS_INITIAL_SIZE;
                }
            }

            sequentialLetters = SEQUENTIAL_LETTERS_INITIAL_SIZE;
        }

        // Right to bottom diagonals
        for (int baseRow = base - props.getSimianSequenceSize(); baseRow > 0; baseRow--) {

            for (int row = baseRow, col = base-1; row < base - props.getSimianSequenceSize() + sequentialLetters; row++, col--) {
                if (dnaMatrix[row][col] == dnaMatrix[row + 1][col - 1]) {
                    sequentialLetters++;

                    if (sequentialLetters == props.getSimianSequenceSize()) {
                        totalCompleteSequences++;

                        if (totalCompleteSequences >= props.getMinimumSimianSequences()) {
                            return true;
                        }

                        sequentialLetters = SEQUENTIAL_LETTERS_INITIAL_SIZE;
                    }
                } else {
                    sequentialLetters = SEQUENTIAL_LETTERS_INITIAL_SIZE;
                }
            }

            sequentialLetters = SEQUENTIAL_LETTERS_INITIAL_SIZE;
        }

        // Top to right diagonals
        for (int baseCol = base - props.getSimianSequenceSize(); baseCol >= 0; baseCol--) {

            for (int row = 0, col = baseCol; col < base - props.getSimianSequenceSize() + sequentialLetters; row++, col++) {
                if (dnaMatrix[row][col] == dnaMatrix[row+1][col+1]) {
                    sequentialLetters++;

                    if (sequentialLetters == props.getSimianSequenceSize()) {
                        totalCompleteSequences++;

                        if (totalCompleteSequences >= props.getMinimumSimianSequences()) {
                            return true;
                        }

                        sequentialLetters = SEQUENTIAL_LETTERS_INITIAL_SIZE;
                    }
                } else {
                    sequentialLetters = SEQUENTIAL_LETTERS_INITIAL_SIZE;
                }
            }

            sequentialLetters = SEQUENTIAL_LETTERS_INITIAL_SIZE;
        }

        // Left to bottom diagonals
        for (int baseRow = base - props.getSimianSequenceSize(); baseRow > 0; baseRow--) {

            for (int row = baseRow, col = 0; row < base - props.getSimianSequenceSize() + sequentialLetters; row++, col++) {
                if (dnaMatrix[row][col] == dnaMatrix[row + 1][col + 1]) {
                    sequentialLetters++;

                    if (sequentialLetters == props.getSimianSequenceSize()) {
                        totalCompleteSequences++;

                        if (totalCompleteSequences >= props.getMinimumSimianSequences()) {
                            return true;
                        }

                        sequentialLetters = SEQUENTIAL_LETTERS_INITIAL_SIZE;
                    }
                } else {
                    sequentialLetters = SEQUENTIAL_LETTERS_INITIAL_SIZE;
                }
            }

            sequentialLetters = SEQUENTIAL_LETTERS_INITIAL_SIZE;
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
