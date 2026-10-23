package com.github.andriilab.promasy.data.reports.models;

import com.github.andriilab.promasy.app.commons.Labels;
import com.github.andriilab.promasy.domain.bid.entities.Bid;
import com.github.andriilab.promasy.domain.bid.entities.CpvAmount;
import com.github.andriilab.promasy.domain.bid.enums.BidType;
import com.github.andriilab.promasy.domain.bid.enums.ProcurementProcedure;
import com.github.andriilab.promasy.domain.cpv.entities.Cpv;
import com.github.andriilab.promasy.domain.finance.entities.Finance;
import com.github.andriilab.promasy.domain.finance.entities.FinanceDepartment;
import com.github.andriilab.promasy.testutil.TestEntities;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class CpvAmountReportModelTest {

    private final Cpv cpv = new Cpv("33100000-1", "Medical equipment", "Medical equipment", 2, false);

    private static Bid bidWithKpkvk(long id, Integer kpkvk) {
        Finance finance = TestEntities.finance("fin-" + id, "Finance", "0", "0", "0");
        finance.setKpkvk(kpkvk);
        FinanceDepartment department = TestEntities.financeDepartment(id, TestEntities.subdepartment("dep", "subdep"));
        department.setFinances(finance);
        Bid bid = TestEntities.bid(id, BidType.MATERIALS, "1", 1);
        bid.setFinances(department);
        return bid;
    }

    private CpvAmount amountOf(String total, Bid... bids) {
        CpvAmount amount = new CpvAmount(cpv, BidType.MATERIALS, new BigDecimal(total), bids[0]);
        for (int i = 1; i < bids.length; i++) {
            amount.addBidModel(bids[i]);
        }
        return amount;
    }

    @Test
    void constructor_amount_copiesCpvTypeAndTotal() {
        CpvAmount amount = amountOf("100", bidWithKpkvk(1, 2201010));

        CpvAmountReportModel model = new CpvAmountReportModel(amount);

        assertThat(model.getParentModel()).isSameAs(amount);
        assertThat(model.getBidType()).isEqualTo(BidType.MATERIALS.getBidTypeName());
        assertThat(model.getCpvNumber()).isEqualTo("33100000-1");
        assertThat(model.getCpvName()).isEqualTo("Medical equipment");
        assertThat(model.getTotalPrice()).isEqualByComparingTo("100");
        assertThat(model.getStartDate()).isEqualTo(Labels.getProperty("duringYear"));
        assertThat(model.getNotation()).isNull();
    }

    @ParameterizedTest
    @CsvSource({
            "0,         BELOW_THRESHOLD",
            "49999.99,  BELOW_THRESHOLD",
            "50000,     BELOW_THRESHOLD",
            "50000.00,  BELOW_THRESHOLD",
            "50000.01,  OPEN_AUCTION",
            "1000000,   OPEN_AUCTION"
    })
    void constructor_totalAroundThreshold_picksProcedure(String total, ProcurementProcedure expected) {
        CpvAmountReportModel model = new CpvAmountReportModel(amountOf(total, bidWithKpkvk(1, 2201010)));

        assertThat(model.getProcurementProcedure()).isEqualTo(expected.toString());
    }

    @Test
    void constructor_kpkvkMissing_usesDefaultFromBundle() {
        CpvAmountReportModel model = new CpvAmountReportModel(amountOf("1", bidWithKpkvk(1, null)));

        assertThat(model.getKpkvk()).isEqualTo(Labels.getProperty("default.kpkvk"));
    }

    @Test
    void constructor_sameKpkvkInSeveralBids_isListedOnce() {
        CpvAmountReportModel model = new CpvAmountReportModel(
                amountOf("1", bidWithKpkvk(1, 2201010), bidWithKpkvk(2, 2201010)));

        assertThat(model.getKpkvk()).isEqualTo("2201010");
    }

    @Test
    void constructor_severalKpkvk_joinsSortedWithCommaAndSpace() {
        CpvAmountReportModel model = new CpvAmountReportModel(
                amountOf("1", bidWithKpkvk(1, 6541030), bidWithKpkvk(2, 2201010), bidWithKpkvk(3, 3000000)));

        assertThat(model.getKpkvk()).isEqualTo("2201010, 3000000, 6541030");
    }
}
