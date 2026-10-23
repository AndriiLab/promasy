package com.github.andriilab.promasy.app.commons;

import org.junit.jupiter.api.Test;

import java.util.MissingResourceException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LabelsTest {

    private static final String KEY = "yes";

    @Test
    void getProperty_existingKey_returnsBundleValue() {
        assertThat(Labels.getProperty(KEY)).isNotEmpty();
    }

    @Test
    void getProperty_missingKey_throwsMissingResource() {
        assertThatThrownBy(() -> Labels.getProperty("no.such.key")).isInstanceOf(MissingResourceException.class);
    }

    @Test
    void getInt_numericKey_parsesValue() {
        assertThat(Labels.getInt("default.bidType.materialKEKV")).isPositive();
    }

    @Test
    void withColon_appendsColonAndSpace() {
        assertThat(Labels.withColon(KEY)).isEqualTo(Labels.getProperty(KEY) + ": ");
    }

    @Test
    void withDot_appendsDot() {
        assertThat(Labels.withDot(KEY)).isEqualTo(Labels.getProperty(KEY) + ".");
    }

    @Test
    void withThreeDots_appendsThreeDots() {
        assertThat(Labels.withThreeDots(KEY)).isEqualTo(Labels.getProperty(KEY) + "...");
    }

    @Test
    void withSpaceBefore_prependsSpace() {
        assertThat(Labels.withSpaceBefore(KEY)).isEqualTo(" " + Labels.getProperty(KEY));
    }

    @Test
    void withSpaceAfter_appendsSpace() {
        assertThat(Labels.withSpaceAfter(KEY)).isEqualTo(Labels.getProperty(KEY) + " ");
    }

    @Test
    void quoted_wrapsInSingleQuotes() {
        assertThat(Labels.quoted(KEY)).isEqualTo("'" + Labels.getProperty(KEY) + "'");
    }

    @Test
    void getVersion_isDottedNumericVersion() {
        assertThat(Labels.getVersion()).matches("[0-9]+(\\.[0-9]+)+");
    }

    @Test
    void getBuildYear_matchesYearOfBuildDate() {
        assertThat(Labels.getBuildYear()).isEqualTo(Integer.parseInt(Labels.getBuildDate().substring(0, 4)));
    }
}
