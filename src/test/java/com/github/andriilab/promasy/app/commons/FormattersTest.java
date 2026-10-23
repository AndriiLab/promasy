package com.github.andriilab.promasy.app.commons;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FormattersTest {

    @ParameterizedTest
    @CsvSource({
            "100,        100",
            "12.5,       12.5",
            "12.50,      12.50",
            "1.5,        1.5",
            "1;5,        1.5",
            "1;50,       1.50",
            "0;05,       0.05",
            "1 234;56,   1234.56",
            "1 234.56,   1234.56",
            "1 000,      1000"
    })
    void formatFinanceString_validInput_normalisesSeparatorAndSpaces(String input, String expected) {
        // ';' stands in for ',' in the table, because ',' separates the CSV columns
        assertThat(Formatters.formatFinanceString(input.replace(';', ','))).isEqualTo(expected);
    }

    @ParameterizedTest
    @ValueSource(strings = {"1,234", "12,345", "1,2,3", "0,001", "12.345", "1 234.567", "0.001"})
    void formatFinanceString_moreThanTwoDecimals_throwsNumberFormatException(String input) {
        assertThatThrownBy(() -> Formatters.formatFinanceString(input)).isInstanceOf(NumberFormatException.class);
    }
}
