package com.github.andriilab.promasy.domain.bid.enums;

import com.github.andriilab.promasy.app.commons.Labels;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class BidTypeTest {

    @ParameterizedTest
    @CsvSource({
            "MATERIALS,bidType.material,default.bidType.materialKEKV",
            "EQUIPMENT,bidType.equipment,default.bidType.equipmentKEKV",
            "SERVICES,bidType.services,default.bidType.servicesKEKV"
    })
    void values_takeNameAndKekvFromLabelsBundle(BidType type, String nameKey, String kekvKey) {
        assertThat(type.getBidTypeName()).isEqualTo(Labels.getProperty(nameKey));
        assertThat(type.getKekv()).isEqualTo(Labels.getInt(kekvKey));
    }

    @ParameterizedTest
    @CsvSource({"MATERIALS", "EQUIPMENT", "SERVICES"})
    void toString_isLocalizedName(BidType type) {
        assertThat(type).hasToString(type.getBidTypeName());
        assertThat(type.getBidTypeName()).isNotBlank();
    }
}
