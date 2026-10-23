package com.github.andriilab.promasy.data.repositories;

import com.github.andriilab.promasy.data.queries.bids.GetBidsQuery;
import com.github.andriilab.promasy.data.queries.cpv.CpvRequestQuery;
import com.github.andriilab.promasy.data.queries.employees.GetEmployeesQuery;
import com.github.andriilab.promasy.data.queries.finance.GetFinanceLeftAmountQuery;
import com.github.andriilab.promasy.data.queries.finance.GetFinanceUnassignedAmountQuery;
import com.github.andriilab.promasy.data.queries.finance.GetFinancesQuery;
import com.github.andriilab.promasy.data.queries.financepartment.GetFinanceDepartmentLeftAmountQuery;
import com.github.andriilab.promasy.data.queries.financepartment.GetFinanceDepartmentsQuery;
import com.github.andriilab.promasy.domain.bid.entities.AmountUnit;
import com.github.andriilab.promasy.domain.bid.enums.BidType;
import com.github.andriilab.promasy.domain.finance.entities.Finance;
import com.github.andriilab.promasy.domain.finance.entities.FinanceDepartment;
import com.github.andriilab.promasy.domain.organization.entities.Department;
import com.github.andriilab.promasy.domain.organization.entities.Institute;
import com.github.andriilab.promasy.domain.organization.entities.Subdepartment;
import com.github.andriilab.promasy.domain.organization.enums.Role;
import com.github.andriilab.promasy.it.AbstractIntegrationTest;
import com.github.andriilab.promasy.it.ItFixtures;
import com.github.andriilab.promasy.it.PostgresContainerSupport;
import com.github.andriilab.promasy.it.Sql;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.function.Function;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Select-only repository methods run without a transaction. Before, each read committed a transaction, and the commit
 * flushed whatever the caller had edited on the shared {@code EntityManager} without saving it.
 */
class ReadOnlyQueriesIT extends AbstractIntegrationTest {

    private static final int YEAR = 2024;

    private Institute institute;
    private Department department;
    private Subdepartment subdepartment;
    private Finance finance;
    private FinanceDepartment financeDepartment;
    private AmountUnit pending;

    /** One read, named for the test report. */
    static final class Read {
        final String name;
        final Function<ReadOnlyQueriesIT, Object> call;

        Read(String name, Function<ReadOnlyQueriesIT, Object> call) {
            this.name = name;
            this.call = call;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    static Stream<Arguments> reads() {
        return Stream.of(
                new Read("Repository.getResults", t -> storage.AMOUNTUNITS.getResults()),
                new Read("EMPLOYEES.retrieve", t -> storage.EMPLOYEES.retrieve(new GetEmployeesQuery())),
                new Read("EMPLOYEES.retrieve(role)", t -> storage.EMPLOYEES.retrieve(new GetEmployeesQuery(Role.ADMIN))),
                new Read("EMPLOYEES.retrieve(role, department)", t -> storage.EMPLOYEES.retrieve(
                        new GetEmployeesQuery(Role.ADMIN, t.department.getModelId()))),
                new Read("EMPLOYEES.getSalt", t -> storage.EMPLOYEES.getSalt("nobody")),
                new Read("EMPLOYEES.checkLogin", t -> storage.EMPLOYEES.checkLogin("nobody")),
                new Read("EMPLOYEES.isFirstRun", t -> storage.EMPLOYEES.isFirstRun()),
                new Read("EMPLOYEES.getUserWithId", t -> storage.EMPLOYEES.getUserWithId(PostgresContainerSupport.ADMIN_ID)),
                new Read("DEPARTMENTS.get", t -> storage.DEPARTMENTS.get(t.institute.getModelId())),
                new Read("SUBDEPARTMENS.get", t -> storage.SUBDEPARTMENS.get(t.department.getModelId())),
                new Read("FINANCES.get(year)", t -> storage.FINANCES.get(new GetFinancesQuery(YEAR))),
                new Read("FINANCES.get(year, department)", t -> storage.FINANCES.get(new GetFinancesQuery(YEAR, t.department))),
                new Read("FINANCES.retrieveUnassignedAmount", t -> storage.FINANCES.retrieveUnassignedAmount(
                        new GetFinanceUnassignedAmountQuery(t.finance, BidType.MATERIALS))),
                new Read("FINANCES.retrieveLeftAmount", t -> storage.FINANCES.retrieveLeftAmount(
                        new GetFinanceLeftAmountQuery(t.finance, BidType.MATERIALS))),
                new Read("DEPARTMENT_FINANCES.get(subdepartment)", t -> storage.DEPARTMENT_FINANCES.get(
                        new GetFinanceDepartmentsQuery(YEAR, t.subdepartment))),
                new Read("DEPARTMENT_FINANCES.get(finance)", t -> storage.DEPARTMENT_FINANCES.get(
                        new GetFinanceDepartmentsQuery(YEAR, t.finance))),
                new Read("DEPARTMENT_FINANCES.getResults", t -> storage.DEPARTMENT_FINANCES.getResults()),
                new Read("DEPARTMENT_FINANCES.retrieveLeftAmount", t -> storage.DEPARTMENT_FINANCES.retrieveLeftAmount(
                        new GetFinanceDepartmentLeftAmountQuery(t.financeDepartment, BidType.MATERIALS))),
                new Read("BIDS.retrieve", t -> storage.BIDS.retrieve(new GetBidsQuery(YEAR))),
                new Read("BIDS.getTotalAmount", t -> storage.BIDS.getTotalAmount(t.financeDepartment, BidType.MATERIALS)),
                new Read("CPV.get", t -> storage.CPV.get(new CpvRequestQuery("031", 1))),
                new Read("CPV.validateCode", t -> storage.CPV.validateCode("03111100-3")),
                new Read("VERSIONS.retrieve", t -> storage.VERSIONS.retrieve()),
                new Read("REGISTRATION.getRegistrationsLeft", t -> storage.REGISTRATION.getRegistrationsLeft()),
                new Read("ServerRepository.getServerTimestamp", t -> ServerRepository.getServerTimestamp())
        ).map(Arguments::of);
    }

    @BeforeEach
    void createFixturesAndPendingEdit() {
        institute = ItFixtures.institute(storage, "Institute");
        department = ItFixtures.department(storage, institute, "Department");
        subdepartment = ItFixtures.subdepartment(storage, department, "Subdepartment");
        finance = ItFixtures.finance(storage, "F", "2024-01-01", "2024-12-31", "1000", "1000", "1000");
        financeDepartment = ItFixtures.financeDepartment(storage, finance, subdepartment, "500", "500", "500");
        ItFixtures.bid(storage, financeDepartment, BidType.MATERIALS, "10", 2);

        pending = new AmountUnit("original");
        storage.AMOUNTUNITS.create(pending);
        // edited on the managed entity, but not saved yet: the user hasn't pressed "save"
        pending.setDescription("pending");
    }

    private Object savedDescription() {
        return Sql.scalar("SELECT amount_unit_desc FROM promasy.amount_units WHERE id = ?", pending.getModelId());
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("reads")
    void read_pendingEditOnSharedEntityManager_isNotWritten(Read read) {
        read.call.apply(this);

        assertThat(savedDescription()).isEqualTo("original");
        assertThat(em.getTransaction().isActive()).isFalse();
    }

    /** Outside a transaction Hibernate hands the connection back after the query, so it isn't left inside one. */
    @ParameterizedTest(name = "{0}")
    @MethodSource("reads")
    void read_noTransaction_leavesNoDatabaseTransactionOpen(Read read) {
        read.call.apply(this);

        assertThat(Sql.count("SELECT count(*) FROM pg_stat_activity"
                + " WHERE usename = 'promasy_user' AND state LIKE 'idle in transaction%'")).isZero();
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("reads")
    void update_afterRead_writesPendingEditOnceWithModificationAudit(Read read) {
        read.call.apply(this);

        storage.AMOUNTUNITS.update(pending);

        long id = pending.getModelId();
        assertThat(savedDescription()).isEqualTo("pending");
        assertThat(Sql.scalar("SELECT version FROM promasy.amount_units WHERE id = ?", id)).isEqualTo(1L);
        assertThat(Sql.scalar("SELECT modified_by FROM promasy.amount_units WHERE id = ?", id))
                .isEqualTo(admin.getModelId());
    }
}
