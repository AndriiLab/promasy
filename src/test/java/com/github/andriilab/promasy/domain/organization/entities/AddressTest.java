package com.github.andriilab.promasy.domain.organization.entities;

import com.github.andriilab.promasy.app.commons.Labels;
import com.github.andriilab.promasy.domain.organization.enums.CityTypes;
import com.github.andriilab.promasy.domain.organization.enums.StreetTypes;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AddressTest {

    private static Address address(StreetTypes streetType, String corpusNumber) {
        return new Address("Ukraine", "Kyiv", CityTypes.CITY, "Kyiv", streetType, "Main", "5", corpusNumber, "01001");
    }

    @Test
    void toString_allFieldsSet_listsPartsInOrder() {
        String expected = StreetTypes.STREET.getShortName() + " Main, 5, 2, Kyiv, Kyiv"
                + Labels.withSpaceBefore("region") + ", Ukraine, 01001";

        assertThat(address(StreetTypes.STREET, "2")).hasToString(expected);
    }

    @Test
    void toString_noCorpusNumber_omitsIt() {
        String expected = StreetTypes.STREET.getShortName() + " Main, 5, Kyiv, Kyiv"
                + Labels.withSpaceBefore("region") + ", Ukraine, 01001";

        assertThat(address(StreetTypes.STREET, null)).hasToString(expected);
    }

    @Test
    void toString_noStreetType_omitsIt() {
        String expected = "Main, 5, 2, Kyiv, Kyiv" + Labels.withSpaceBefore("region") + ", Ukraine, 01001";

        assertThat(address(null, "2")).hasToString(expected);
    }

    @Test
    void description_isAlwaysEmpty() {
        Address address = address(StreetTypes.STREET, null);

        address.setDescription("ignored");

        assertThat(address.getDescription()).isEmpty();
    }

    @Test
    void message_isEmpty() {
        assertThat(new Address().getMessage()).isEmpty();
    }
}
