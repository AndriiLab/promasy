package com.github.andriilab.promasy.domain.versioning.entities;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class VersionTest {

    @Test
    void constructor_null_throwsIllegalArgument() {
        assertThatThrownBy(() -> new Version(null)).isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"1", "1.", ".1", "a.b", "1.0-beta", "1..0", "1.0 ", "v1.0"})
    void constructor_invalidFormat_throwsIllegalArgument(String value) {
        assertThatThrownBy(() -> new Version(value)).isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"1.0", "0.8.3.4", "10.20.30", "0.0"})
    void constructor_validFormat_keepsValue(String value) {
        assertThat(new Version(value).getVersion()).isEqualTo(value);
    }

    @ParameterizedTest
    @CsvSource({
            "0.8.3.4, 0.8.10,  -1",
            "0.8.10,  0.8.3.4,  1",
            "1.0,     1.0.0,    0",
            "1.0.0,   1.0,      0",
            "1.2,     1.10,    -1",
            "2.0,     1.9.9,    1",
            "1.0.1,   1.0,      1",
            "1.0,     1.0.1,   -1",
            "0.8.3.4, 0.8.3.4,  0"
    })
    void compareTo_versions_comparesNumericallyPartByPart(String left, String right, int expectedSign) {
        assertThat(Integer.signum(new Version(left).compareTo(new Version(right)))).isEqualTo(expectedSign);
    }

    @Test
    void compareTo_null_returnsOne() {
        assertThat(new Version("1.0").compareTo(null)).isEqualTo(1);
    }

    @Test
    void equals_sameInstance_isTrue() {
        Version version = new Version("1.0");

        assertThat(version.equals(version)).isTrue();
    }

    @Test
    void equals_trailingZeroParts_isTrueInBothDirections() {
        Version shorter = new Version("1.0");
        Version longer = new Version("1.0.0");

        assertThat(shorter.equals(longer)).isTrue();
        assertThat(longer.equals(shorter)).isTrue();
    }

    @Test
    void equals_differentVersion_isFalse() {
        assertThat(new Version("1.0").equals(new Version("1.1"))).isFalse();
    }

    @Test
    void equals_nullOrOtherType_isFalse() {
        Version version = new Version("1.0");

        assertThat(version.equals(null)).isFalse();
        assertThat(version.equals("1.0")).isFalse();
    }

    @ParameterizedTest
    @CsvSource({
            "1.0,     1.0.0",
            "1.0,     1.0.0.0",
            "0.8.3.4, 0.8.3.4",
            "2.10,    2.10.0"
    })
    void hashCode_equalVersions_areEqual(String left, String right) {
        Version a = new Version(left);
        Version b = new Version(right);

        assertThat(a).isEqualTo(b);
        assertThat(a.hashCode()).isEqualTo(b.hashCode());
    }

    @Test
    void hashCode_noVersionSet_doesNotThrow() {
        assertThat(new Version().hashCode()).isEqualTo(new Version().hashCode());
    }
}
