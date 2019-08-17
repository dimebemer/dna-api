package com.mercadolibre.melidnaapi.business.validator;

import com.mercadolibre.melidnaapi.config.props.DnaProperties;
import com.mercadolibre.melidnaapi.model.exception.BusinessException;
import com.mercadolibre.melidnaapi.model.table.Dna;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.text.MessageFormat.format;

@Component
@RequiredArgsConstructor
public class MatrixProportionValidator implements DnaValidator {

    private final DnaProperties props;

    @Override
    public void validate(Dna dna) {
        List<String> rows = dna.getDna();

        rows.forEach(row -> validateIfRowAttendsSquareSize(row, rows.size()));

        validateMinimumMatrixSize(rows.size());
    }

    private void validateMinimumMatrixSize(int matrixSize) {
        if (matrixSize < props.getMinimumMatrixSize()) {
            throw new BusinessException(format("DNA Matrix must have at least {0} letters per row", props.getMinimumMatrixSize()));
        }
    }

    private void validateIfRowAttendsSquareSize(String row, int rowCount) {
        if (row.length() != rowCount) {
            throw new BusinessException("DNA Matrix must be square");
        }
    }

}
