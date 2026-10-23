package com.github.andriilab.promasy.data.repositories;

import com.github.andriilab.promasy.data.queries.bids.GetBidsQuery;
import com.github.andriilab.promasy.domain.bid.entities.Bid;
import com.github.andriilab.promasy.domain.bid.entities.BidStatus;
import com.github.andriilab.promasy.domain.bid.enums.BidType;
import com.github.andriilab.promasy.domain.bid.enums.Status;
import com.github.andriilab.promasy.domain.finance.entities.Finance;
import com.github.andriilab.promasy.domain.finance.entities.FinanceDepartment;
import com.github.andriilab.promasy.domain.organization.entities.Department;
import com.github.andriilab.promasy.domain.organization.entities.Institute;
import com.github.andriilab.promasy.domain.organization.entities.Subdepartment;
import com.github.andriilab.promasy.it.AbstractIntegrationTest;
import com.github.andriilab.promasy.it.ItFixtures;
import com.github.andriilab.promasy.it.PostgresContainerSupport;
import com.github.andriilab.promasy.it.Sql;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * The bid queries on real SQL: every route of {@code retrieve(GetBidsQuery)}, the total amount, and the statuses.
 * <p>
 * Shared layout (bids are created in the order listed, so the last one is the newest):
 * <pre>
 * department A: subdepartment A1 (finance 2024: bids a1-mat, a1-eq; finance 2020: bid old-mat), subdepartment A2 (a2-mat)
 * department B: subdepartment B1 (finance 2024: b1-mat, b1-srv)
 * </pre>
 */
class BidsRepositoryIT extends AbstractIntegrationTest {

    private Department departmentA;
    private Department departmentB;
    private Subdepartment subdepartmentA1;
    private Subdepartment subdepartmentB1;
    private FinanceDepartment financeDepartmentA1;
    private FinanceDepartment financeDepartmentA1Old;
    private FinanceDepartment financeDepartmentA2;
    private FinanceDepartment financeDepartmentB1;
    private Bid a1Materials;

    private static List<String> descriptions(List<Bid> bids) {
        return bids.stream().map(Bid::getDescription).collect(Collectors.toList());
    }

    @BeforeEach
    void createLayout() {
        Institute institute = ItFixtures.institute(storage, "Institute");
        departmentA = ItFixtures.department(storage, institute, "Department A");
        departmentB = ItFixtures.department(storage, institute, "Department B");
        subdepartmentA1 = ItFixtures.subdepartment(storage, departmentA, "A1");
        Subdepartment subdepartmentA2 = ItFixtures.subdepartment(storage, departmentA, "A2");
        subdepartmentB1 = ItFixtures.subdepartment(storage, departmentB, "B1");
        Finance finance2024 = ItFixtures.finance(storage, "2024", "2024-01-01", "2024-12-31", "1000", "1000", "1000");
        Finance finance2020 = ItFixtures.finance(storage, "2020", "2020-01-01", "2020-12-31", "1000", "1000", "1000");
        financeDepartmentA1 = ItFixtures.financeDepartment(storage, finance2024, subdepartmentA1, "500", "500", "500");
        financeDepartmentA2 = ItFixtures.financeDepartment(storage, finance2024, subdepartmentA2, "500", "500", "500");
        financeDepartmentB1 = ItFixtures.financeDepartment(storage, finance2024, subdepartmentB1, "500", "500", "500");
        financeDepartmentA1Old = ItFixtures.financeDepartment(storage, finance2020, subdepartmentA1, "500", "500", "500");

        a1Materials = ItFixtures.bid(storage, financeDepartmentA1, BidType.MATERIALS, "10", 2, "a1-mat", null);
        ItFixtures.bid(storage, financeDepartmentA1, BidType.EQUIPMENT, "10", 2, "a1-eq", null);
        ItFixtures.bid(storage, financeDepartmentA2, BidType.MATERIALS, "10", 2, "a2-mat", null);
        ItFixtures.bid(storage, financeDepartmentB1, BidType.MATERIALS, "10", 2, "b1-mat", null);
        ItFixtures.bid(storage, financeDepartmentB1, BidType.SERVICES, "10", 2, "b1-srv", null);
        ItFixtures.bid(storage, financeDepartmentA1Old, BidType.MATERIALS, "10", 2, "old-mat", null);
    }

    @Test
    void retrieve_byYear_returnsBidsOfFinancesCoveringTheYearNewestFirst() {
        assertThat(descriptions(storage.BIDS.retrieve(new GetBidsQuery(2024))))
                .containsExactly("b1-srv", "b1-mat", "a2-mat", "a1-eq", "a1-mat");
        assertThat(descriptions(storage.BIDS.retrieve(new GetBidsQuery(2020)))).containsExactly("old-mat");
        assertThat(storage.BIDS.retrieve(new GetBidsQuery(2019))).isEmpty();
        assertThat(storage.BIDS.retrieve(new GetBidsQuery(2025))).isEmpty();
    }

    @Test
    void retrieve_byTypeAndYear_returnsOnlyBidsOfThatType() {
        assertThat(descriptions(storage.BIDS.retrieve(new GetBidsQuery(BidType.MATERIALS, 2024))))
                .containsExactly("b1-mat", "a2-mat", "a1-mat");
        assertThat(descriptions(storage.BIDS.retrieve(new GetBidsQuery(BidType.EQUIPMENT, 2024))))
                .containsExactly("a1-eq");
        assertThat(descriptions(storage.BIDS.retrieve(new GetBidsQuery(BidType.SERVICES, 2024))))
                .containsExactly("b1-srv");
        assertThat(descriptions(storage.BIDS.retrieve(new GetBidsQuery(BidType.MATERIALS, 2020))))
                .containsExactly("old-mat");
        assertThat(storage.BIDS.retrieve(new GetBidsQuery(BidType.EQUIPMENT, 2020))).isEmpty();
    }

    @Test
    void retrieve_byDepartment_returnsBidsOfAllItsSubdepartmentsOfThatTypeAndYear() {
        assertThat(descriptions(storage.BIDS.retrieve(new GetBidsQuery(BidType.MATERIALS, 2024, departmentA))))
                .containsExactly("a2-mat", "a1-mat");
        assertThat(descriptions(storage.BIDS.retrieve(new GetBidsQuery(BidType.EQUIPMENT, 2024, departmentA))))
                .containsExactly("a1-eq");
        assertThat(storage.BIDS.retrieve(new GetBidsQuery(BidType.SERVICES, 2024, departmentA))).isEmpty();
        assertThat(descriptions(storage.BIDS.retrieve(new GetBidsQuery(BidType.MATERIALS, 2024, departmentB))))
                .containsExactly("b1-mat");
        assertThat(descriptions(storage.BIDS.retrieve(new GetBidsQuery(BidType.MATERIALS, 2020, departmentA))))
                .containsExactly("old-mat");
        assertThat(storage.BIDS.retrieve(new GetBidsQuery(BidType.MATERIALS, 2020, departmentB))).isEmpty();
    }

    @Test
    void retrieve_bySubdepartment_returnsBidsOfThatSubdepartmentOfThatTypeAndYear() {
        assertThat(descriptions(storage.BIDS.retrieve(new GetBidsQuery(BidType.MATERIALS, 2024, subdepartmentA1))))
                .containsExactly("a1-mat");
        assertThat(descriptions(storage.BIDS.retrieve(new GetBidsQuery(BidType.MATERIALS, 2020, subdepartmentA1))))
                .containsExactly("old-mat");
        assertThat(descriptions(storage.BIDS.retrieve(new GetBidsQuery(BidType.SERVICES, 2024, subdepartmentB1))))
                .containsExactly("b1-srv");
        assertThat(storage.BIDS.retrieve(new GetBidsQuery(BidType.SERVICES, 2024, subdepartmentA1))).isEmpty();
    }

    @Test
    void retrieve_byFinanceDepartment_returnsBidsOfThatAssignmentOfThatTypeAndYear() {
        assertThat(descriptions(storage.BIDS.retrieve(new GetBidsQuery(BidType.MATERIALS, 2024, financeDepartmentA1))))
                .containsExactly("a1-mat");
        assertThat(descriptions(storage.BIDS.retrieve(new GetBidsQuery(BidType.EQUIPMENT, 2024, financeDepartmentA1))))
                .containsExactly("a1-eq");
        // the same subdepartment, but another finance
        assertThat(descriptions(storage.BIDS.retrieve(new GetBidsQuery(BidType.MATERIALS, 2020, financeDepartmentA1Old))))
                .containsExactly("old-mat");
        // the assignment's finance does not cover the requested year
        assertThat(storage.BIDS.retrieve(new GetBidsQuery(BidType.MATERIALS, 2024, financeDepartmentA1Old))).isEmpty();
    }

    @Test
    void retrieve_everyRoute_leavesOutSoftDeletedBids() {
        Bid removed = ItFixtures.bid(storage, financeDepartmentA1, BidType.MATERIALS, "10", 2, "removed", null);
        storage.BIDS.delete(removed);

        assertThat(descriptions(storage.BIDS.retrieve(new GetBidsQuery(2024)))).doesNotContain("removed");
        assertThat(descriptions(storage.BIDS.retrieve(new GetBidsQuery(BidType.MATERIALS, 2024)))).doesNotContain("removed");
        assertThat(descriptions(storage.BIDS.retrieve(new GetBidsQuery(BidType.MATERIALS, 2024, departmentA))))
                .doesNotContain("removed");
        assertThat(descriptions(storage.BIDS.retrieve(new GetBidsQuery(BidType.MATERIALS, 2024, subdepartmentA1))))
                .doesNotContain("removed");
        assertThat(descriptions(storage.BIDS.retrieve(new GetBidsQuery(BidType.MATERIALS, 2024, financeDepartmentA1))))
                .doesNotContain("removed");
        assertThat(descriptions(storage.BIDS.getResults())).doesNotContain("removed").hasSize(6);
        // the row is still there
        assertThat(Sql.scalar("SELECT active FROM promasy.bids WHERE id = ?", removed.getModelId())).isEqualTo(false);
    }

    @Test
    void retrieve_afterBidWasUpdated_putsItFirst() {
        a1Materials.setAmount(7);
        storage.BIDS.update(a1Materials);

        assertThat(descriptions(storage.BIDS.retrieve(new GetBidsQuery(2024))).get(0)).isEqualTo("a1-mat");
        assertThat(descriptions(storage.BIDS.retrieve(new GetBidsQuery(BidType.MATERIALS, 2024))).get(0))
                .isEqualTo("a1-mat");
    }

    @Test
    void retrieve_afterRowWasChangedOutsideHibernate_returnsCurrentValues() {
        storage.BIDS.retrieve(new GetBidsQuery(2024));
        Sql.execute("UPDATE promasy.bids SET amount = 99, one_price = 1.50 WHERE id = ?", a1Materials.getModelId());

        Bid reloaded = storage.BIDS.retrieve(new GetBidsQuery(BidType.MATERIALS, 2024, financeDepartmentA1)).get(0);

        assertThat(reloaded.getAmount()).isEqualTo(99);
        assertThat(reloaded.getOnePrice()).isEqualByComparingTo("1.50");
    }

    @Test
    void getTotalAmount_sumsPriceTimesAmountOfActiveBidsOfThatTypeAndAssignment() {
        ItFixtures.bid(storage, financeDepartmentA1, BidType.MATERIALS, "2.50", 4, "a1-mat-2", null);
        Bid removed = ItFixtures.bid(storage, financeDepartmentA1, BidType.MATERIALS, "1000", 1, "removed", null);
        storage.BIDS.delete(removed);

        // a1-mat: 10 * 2, a1-mat-2: 2.50 * 4, the soft-deleted bid, the other type and the other assignments don't count
        assertThat(storage.BIDS.getTotalAmount(financeDepartmentA1, BidType.MATERIALS)).isEqualByComparingTo("30");
        assertThat(storage.BIDS.getTotalAmount(financeDepartmentA1, BidType.EQUIPMENT)).isEqualByComparingTo("20");
        assertThat(storage.BIDS.getTotalAmount(financeDepartmentA2, BidType.MATERIALS)).isEqualByComparingTo("20");
    }

    @Test
    void getTotalAmount_noBids_returnsZero() {
        assertThat(storage.BIDS.getTotalAmount(financeDepartmentA1, BidType.SERVICES)).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    void create_withInitialStatus_persistsStatusThroughTheCascade() {
        Bid bid = new Bid(null, null, "with-status", null, new BigDecimal("5"), 3, null, financeDepartmentA1, null,
                new java.util.ArrayList<>(), null, BidType.MATERIALS, null);
        financeDepartmentA1.addBid(bid);
        // like CreateBidPanel: the first status is added to the new bid before it is saved
        BidStatus created = new BidStatus(Status.CREATED, bid);
        created.setCreatedDate(ServerRepository.getServerTimestamp());
        created.setCreatedEmployee(admin);
        bid.getStatuses().add(created);

        storage.BIDS.create(bid);

        assertThat(Sql.count("SELECT count(*) FROM promasy.bid_statuses WHERE bid_id = ?", bid.getModelId()))
                .isEqualTo(1);
        assertThat(Sql.scalar("SELECT status FROM promasy.bid_statuses WHERE bid_id = ?", bid.getModelId()))
                .isEqualTo("CREATED");
        assertThat(Sql.scalar("SELECT created_by FROM promasy.bid_statuses WHERE bid_id = ?", bid.getModelId()))
                .isEqualTo(PostgresContainerSupport.ADMIN_ID);
    }

    @Test
    void createStatus_afterBidExists_isReturnedByRetrieveAsTheLastStatus() {
        BidStatus created = new BidStatus(Status.CREATED, a1Materials);
        a1Materials.addStatus(created);
        storage.BID_STATUSES.create(created);
        storage.BIDS.update(a1Materials);
        BidStatus submitted = new BidStatus(Status.SUBMITTED, a1Materials);
        a1Materials.addStatus(submitted);
        storage.BID_STATUSES.create(submitted);
        storage.BIDS.update(a1Materials);

        Bid reloaded = storage.BIDS.retrieve(new GetBidsQuery(BidType.MATERIALS, 2024, financeDepartmentA1)).get(0);

        assertThat(reloaded.getStatuses()).extracting(BidStatus::getStatus)
                .containsExactlyInAnyOrder(Status.CREATED, Status.SUBMITTED);
        assertThat(reloaded.getLastBidStatusModel().getStatus()).isEqualTo(Status.SUBMITTED);
    }

    @Test
    void delete_bidWithStatuses_deactivatesTheStatusesToo() {
        BidStatus created = new BidStatus(Status.CREATED, a1Materials);
        a1Materials.addStatus(created);
        storage.BID_STATUSES.create(created);
        storage.BIDS.update(a1Materials);

        storage.BIDS.delete(a1Materials);

        assertThat(Sql.count("SELECT count(*) FROM promasy.bid_statuses WHERE bid_id = ? AND active", a1Materials.getModelId()))
                .isZero();
        assertThat(Sql.count("SELECT count(*) FROM promasy.bid_statuses WHERE bid_id = ?", a1Materials.getModelId()))
                .isEqualTo(1);
    }
}
