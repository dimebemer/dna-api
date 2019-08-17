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

    private final DnaProperties props;

    public DnaCategory categorize(Dna dna) {
        return isSimian(dna.getDna()) ? SIMIAN : HUMAN;
    }

    private boolean isSimian(List<String> dna) {
        char[][] dnaMatrix = fetchDnaMatrix(dna);

        final int base = dna.size();

        int total4LetterSequences = 0;
        int seqLetters = 1;

        // Check horizontally
        for (int row = 0; row < base; row++) {
            int col = 0;

            while (col < base - props.getSequenceSize() + seqLetters) {
                if (dnaMatrix[row][col] == dnaMatrix[row][col+1]) {
                    seqLetters++;

                    if (seqLetters == props.getSequenceSize()) {
                        total4LetterSequences++;

                        if (total4LetterSequences >= props.getMinimumSimianSequences()) {
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

            while (row < base - props.getSequenceSize() + seqLetters) {
                if (dnaMatrix[row][col] == dnaMatrix[row+1][col]) {
                    seqLetters++;

                    if (seqLetters == props.getSequenceSize()) {
                        total4LetterSequences++;

                        if (total4LetterSequences >= props.getMinimumSimianSequences()) {
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

        // Top to left diagonals
        for (int baseCol = props.getSequenceSize() - 1; baseCol < base; baseCol++) {

            for (int row=0, col=baseCol; col >= props.getSequenceSize() - seqLetters; row++, col--) {
                if (dnaMatrix[row][col] == dnaMatrix[row+1][col-1]) {
                    seqLetters++;

                    if (seqLetters == props.getSequenceSize()) {
                        total4LetterSequences++;

                        if (total4LetterSequences >= props.getMinimumSimianSequences()) {
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

        // Right to bottom diagonals
        for (int baseRow = base - props.getSequenceSize(); baseRow > 0; baseRow--) {

            for (int row=baseRow, col=base-1; row < base - props.getSequenceSize() + seqLetters; row++, col--) {
                if (dnaMatrix[row][col] == dnaMatrix[row + 1][col - 1]) {
                    seqLetters++;

                    if (seqLetters == props.getSequenceSize()) {
                        total4LetterSequences++;

                        if (total4LetterSequences >= props.getMinimumSimianSequences()) {
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

        // Top to right diagonals
        for (int baseCol = base - props.getSequenceSize(); baseCol > 0; baseCol--) {

            for (int row=0, col=baseCol; col < base - props.getSequenceSize() + seqLetters; row++, col++) {
                if (dnaMatrix[row][col] == dnaMatrix[row+1][col+1]) {
                    seqLetters++;

                    if (seqLetters == props.getSequenceSize()) {
                        total4LetterSequences++;

                        if (total4LetterSequences >= props.getMinimumSimianSequences()) {
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

        // Left to bottom diagonals
        for (int baseRow = base - props.getSequenceSize(); baseRow > 0; baseRow--) {

            for (int row = baseRow, col = 0; row < base - props.getSequenceSize() + seqLetters; row++, col++) {
                if (dnaMatrix[row][col] == dnaMatrix[row + 1][col + 1]) {
                    seqLetters++;

                    if (seqLetters == props.getSequenceSize()) {
                        total4LetterSequences++;

                        if (total4LetterSequences >= props.getMinimumSimianSequences()) {
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
