package com.github.andriilab.promasy.app.commons;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ParsersTest {

    @ParameterizedTest
    @CsvSource({
            "100,        100",
            "12.5,       12.5",
            "12;50,      12.50",
            "1 234;56,   1234.56",
            "0;05,       0.05",
            "-5,         -5"
    })
    void toFinanceAmount_validInput_returnsExactDecimal(String input, String expected) {
        // ';' stands in for ',' in the table, because ',' separates the CSV columns
        BigDecimal result = Parsers.toFinanceAmount(input.replace(';', ','));

        assertThat(result).isEqualTo(new BigDecimal(expected));
        assertThat(result.scale()).isEqualTo(new BigDecimal(expected).scale());
    }

    @ParameterizedTest
    @ValueSource(strings = {"abc", "1.2.3", "12.345", "1;234", "--5", "1e"})
    void toFinanceAmount_invalidInput_throwsNumberFormatException(String input) {
        assertThatThrownBy(() -> Parsers.toFinanceAmount(input.replace(';', ',')))
                .isInstanceOf(NumberFormatException.class);
    }
}
