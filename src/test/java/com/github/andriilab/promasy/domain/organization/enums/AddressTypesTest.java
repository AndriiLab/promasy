package com.github.andriilab.promasy.domain.organization.enums;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;

class AddressTypesTest {

    @ParameterizedTest
    @EnumSource(StreetTypes.class)
    void streetTypes_haveNameAndShortNameFromLabelsBundle(StreetTypes type) {
        assertThat(type.getName()).isNotBlank();
        assertThat(type.getShortName()).isNotBlank();
        assertThat(type).hasToString(type.getName());
    }

    @ParameterizedTest
    @EnumSource(CityTypes.class)
    void cityTypes_haveNameAndShortNameFromLabelsBundle(CityTypes type) {
        assertThat(type.getName()).isNotBlank();
        assertThat(type.getShortName()).isNotBlank();
        assertThat(type).hasToString(type.getName());
    }
}
