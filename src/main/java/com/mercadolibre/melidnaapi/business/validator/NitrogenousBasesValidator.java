package com.mercadolibre.melidnaapi.business.validator;

import com.mercadolibre.melidnaapi.config.props.DnaProperties;
import com.mercadolibre.melidnaapi.model.exception.BusinessException;
import com.mercadolibre.melidnaapi.model.table.Dna;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Arrays;

import static java.text.MessageFormat.format;

@Component
@RequiredArgsConstructor
public class NitrogenousBasesValidator implements DnaValidator {

    private final DnaProperties props;

    @Override
    public void validate(Dna dna) {
        dna.getDna().forEach(this::validateRow);
    }

    private void validateRow(String row) {
        if (!StringUtils.containsOnly(row, props.getNitrogenousBases())) {
            throw new BusinessException(format("DNA should only contain letters {0}", Arrays.toString(props.getNitrogenousBases())));
        }
    }

}
