package com.github.andriilab.promasy.data.reports.models;

import com.github.andriilab.promasy.app.commons.Labels;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class BidReportModelTest {

    private static BidReportModel model(String producer, String supplier, String reason, String onePrice, int amount) {
        return new BidReportModel("dep", "subdep", "fin-1", "Finance", "Materials", "33100000-1", "CPV",
                "Description", "2020-01-01", producer, "cat-1", supplier, reason, "pcs", new BigDecimal(onePrice), amount);
    }

    @Test
    void constructor_givenValues_exposesThemUnchanged() {
        BidReportModel model = model("Brand", "Supplier", "Cheapest", "1.50", 3);

        assertThat(model.getDepartment()).isEqualTo("dep");
        assertThat(model.getSubdepartment()).isEqualTo("subdep");
        assertThat(model.getFinanceNumber()).isEqualTo("fin-1");
        assertThat(model.getFinanceName()).isEqualTo("Finance");
        assertThat(model.getFinanceType()).isEqualTo("Materials");
        assertThat(model.getCpv()).isEqualTo("33100000-1");
        assertThat(model.getCpvUkr()).isEqualTo("CPV");
        assertThat(model.getOrderDescription()).isEqualTo("Description");
        assertThat(model.getOrderDate()).isEqualTo("2020-01-01");
        assertThat(model.getProducer()).isEqualTo("Brand");
        assertThat(model.getCatNum()).isEqualTo("cat-1");
        assertThat(model.getSupplier()).isEqualTo("Supplier");
        assertThat(model.getReasonForSupplierChoice()).isEqualTo("Cheapest");
        assertThat(model.getPackType()).isEqualTo("pcs");
        assertThat(model.getOnePrice()).isEqualByComparingTo("1.50");
        assertThat(model.getAmount()).isEqualTo(3);
    }

    @ParameterizedTest
    @NullAndEmptySource
    void constructor_missingProducer_fallsBackToAny(String producer) {
        assertThat(model(producer, "Supplier", "r", "1", 1).getProducer()).isEqualTo(Labels.getProperty("any"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void constructor_missingSupplier_fallsBackToAny(String supplier) {
        assertThat(model("Brand", supplier, "r", "1", 1).getSupplier()).isEqualTo(Labels.getProperty("any"));
    }

    @Test
    void constructor_nullReason_becomesEmptyString() {
        assertThat(model("Brand", "Supplier", null, "1", 1).getReasonForSupplierChoice()).isEmpty();
    }

    @ParameterizedTest
    @CsvSource({
            "10,     2, 20.00",
            "0.10,   3, 0.30",
            "1.001,  3, 3.01",
            "1.0001, 1, 1.01",
            "2.50,   0, 0.00",
            "0.333,  3, 1.00"
    })
    void getTotalPrice_multipliesAndRoundsUpToTwoDecimals(String onePrice, int amount, String expected) {
        BigDecimal total = model("Brand", "Supplier", "r", onePrice, amount).getTotalPrice();

        assertThat(total).isEqualTo(new BigDecimal(expected));
        assertThat(total.scale()).isEqualTo(2);
    }
}
