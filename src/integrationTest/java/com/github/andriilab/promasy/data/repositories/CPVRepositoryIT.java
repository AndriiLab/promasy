package com.github.andriilab.promasy.data.repositories;

import com.github.andriilab.promasy.data.queries.bids.GetBidsQuery;
import com.github.andriilab.promasy.data.queries.cpv.CpvRequestQuery;
import com.github.andriilab.promasy.domain.EmptyModel;
import com.github.andriilab.promasy.domain.bid.entities.CpvAmount;
import com.github.andriilab.promasy.domain.bid.enums.BidType;
import com.github.andriilab.promasy.domain.cpv.entities.Cpv;
import com.github.andriilab.promasy.domain.finance.entities.Finance;
import com.github.andriilab.promasy.domain.finance.entities.FinanceDepartment;
import com.github.andriilab.promasy.domain.organization.entities.Department;
import com.github.andriilab.promasy.domain.organization.entities.Institute;
import com.github.andriilab.promasy.domain.organization.entities.Subdepartment;
import com.github.andriilab.promasy.it.AbstractIntegrationTest;
import com.github.andriilab.promasy.it.ItFixtures;
import com.github.andriilab.promasy.it.Sql;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * CPV search against the real CPV dictionary that {@code db_clean.sql} loads (about 10k rows, shared by all tests).
 */
class CPVRepositoryIT extends AbstractIntegrationTest {

    private List<Cpv> search(String request, int depth) {
        return storage.CPV.get(new CpvRequestQuery(request, depth));
    }

    private static List<String> codes(List<Cpv> cpvs) {
        return cpvs.stream().map(Cpv::getCpvId).collect(Collectors.toList());
    }

    private static void assertSortedByCode(List<Cpv> cpvs) {
        List<String> codes = codes(cpvs);
        List<String> sorted = new ArrayList<>(codes);
        Collections.sort(sorted);
        assertThat(codes).isEqualTo(sorted);
    }

    @Test
    void get_emptyRequest_returnsGeneralGroupsOfLevelOneSortedByCode() {
        List<Cpv> result = search("", 0);

        assertThat(result).isNotEmpty().hasSizeLessThanOrEqualTo(100);
        assertThat(result).allMatch(cpv -> cpv.getCpvLevel() == 1);
        assertThat(result.get(0).getCpvId()).isEqualTo("03000000-1");
        assertSortedByCode(result);
    }

    @Test
    void get_digitsAtDepthZero_returnsEveryLevelWithThatPrefix() {
        List<Cpv> result = search("0311", 0);

        assertThat(codes(result)).startsWith("03110000-5", "03111000-2", "03111100-3");
        assertThat(codes(result)).allMatch(code -> code.startsWith("0311"));
        assertThat(result).extracting(Cpv::getCpvLevel).contains(3, 4, 5);
        assertSortedByCode(result);
    }

    @Test
    void get_fullEightDigitCode_findsThatCode() {
        assertThat(codes(search("03111100", 0))).containsExactly("03111100-3");
    }

    @Test
    void get_codeWithCheckDigit_isTrimmedToEightCharacters() {
        assertThat(codes(search("03111100-3", 0))).containsExactly("03111100-3");
    }

    @Test
    void get_oneLevelDown_returnsDirectChildrenOnly() {
        // "03000000" is trimmed to "03" and the level below the group (1) is 2
        List<Cpv> divisionChildren = search("03000000", 1);
        assertThat(divisionChildren).extracting(Cpv::getCpvLevel).containsOnly(2);
        assertThat(codes(divisionChildren)).allMatch(code -> code.startsWith("03"))
                .contains("03100000-2", "03200000-3");

        // "03100000" is trimmed to "031", children are level 3
        List<Cpv> groupChildren = search("03100000", 1);
        assertThat(groupChildren).extracting(Cpv::getCpvLevel).containsOnly(3);
        assertThat(codes(groupChildren)).allMatch(code -> code.startsWith("031"))
                .contains("03110000-5", "03120000-8");
    }

    @Test
    void get_oneLevelUp_returnsTheLevelOfTheParentAndItsSiblings() {
        // "03110000" -> "0311" -> parent prefix "031": the groups of that parent, among them the one we came from
        List<Cpv> result = search("03110000", -1);

        assertThat(result).extracting(Cpv::getCpvLevel).containsOnly(3);
        assertThat(codes(result)).allMatch(code -> code.startsWith("031")).contains("03110000-5", "03120000-8");
    }

    @Test
    void get_oneLevelUpFromShortCode_returnsGeneralGroups() {
        List<Cpv> result = search("03", -1);

        assertThat(result).isNotEmpty();
        assertThat(result).extracting(Cpv::getCpvLevel).containsOnly(1);
        assertThat(result.get(0).getCpvId()).isEqualTo("03000000-1");
    }

    @Test
    void get_englishText_matchesCaseInsensitivelyAnywhereInTheName() {
        List<Cpv> lower = search("soya", 0);
        List<Cpv> upper = search("SOYA", 0);

        assertThat(codes(lower)).contains("03111100-3");
        assertThat(lower).allMatch(cpv -> cpv.getCpvEng().toLowerCase().contains("soya"));
        assertThat(codes(upper)).isEqualTo(codes(lower));
        assertSortedByCode(lower);
    }

    @Test
    void get_ukrainianText_matchesCaseInsensitivelyAnywhereInTheName() {
        List<Cpv> lower = search("зерно сої", 0);
        List<Cpv> upper = search("ЗЕРНО СОЇ", 0);

        assertThat(codes(lower)).contains("03111100-3");
        assertThat(lower).allMatch(cpv -> cpv.getCpvUkr().toLowerCase().contains("зерно сої"));
        assertThat(codes(upper)).isEqualTo(codes(lower));
    }

    @Test
    void get_textWithoutMatch_returnsEmptyList() {
        assertThat(search("zzzzzzzz-no-such-product", 0)).isEmpty();
        assertThat(search("99999999", 0)).isEmpty();
    }

    @Test
    void get_broadRequest_isCappedAtOneHundredRows() {
        assertThat(search("a", 0)).hasSize(100);
        assertThat(search("0", 0)).hasSize(100);
    }

    @Test
    void get_result_isUnmodifiable() {
        List<Cpv> result = search("", 0);

        assertThatThrownBy(() -> result.add(result.get(0))).isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void validateCode_knownCode_returnsThatCpv() {
        Cpv cpv = storage.CPV.validateCode("03111100-3");

        assertThat(cpv.getCpvId()).isEqualTo("03111100-3");
        assertThat(cpv.getCpvEng()).isEqualTo("Soya beans");
        assertThat(cpv.getCpvLevel()).isEqualTo(5);
        assertThat(cpv.isCpvTerminal()).isTrue();
    }

    @Test
    void validateCode_unknownCodeOrCodeWithoutCheckDigit_returnsEmptyModel() {
        // the exact code is compared, including the check digit
        assertThat(storage.CPV.validateCode("03111100")).isSameAs(EmptyModel.CPV);
        assertThat(storage.CPV.validateCode("00000000-0")).isSameAs(EmptyModel.CPV);
        assertThat(storage.CPV.validateCode("")).isSameAs(EmptyModel.CPV);
    }

    @Test
    void create_newCode_isPersistedAndFound() {
        Cpv cpv = new Cpv("99999999-9", "Тест", "Test code", 5, true);
        try {
            storage.CPV.create(cpv);

            assertThat(storage.CPV.validateCode("99999999-9").getCpvEng()).isEqualTo("Test code");
            assertThat(Sql.count("SELECT count(*) FROM promasy.cpv WHERE cpv_code = '99999999-9'")).isEqualTo(1);
        } finally {
            // promasy_user cannot delete, and the cpv table is shared by all tests
            em.detach(cpv);
            Sql.execute("DELETE FROM promasy.cpv WHERE cpv_code = '99999999-9'");
        }
    }

    @Test
    void groupByCpvAndType_bidsLoadedFromDatabase_areGroupedByFourDigitGroupAndTypeWithLookupThroughStorage() {
        Institute institute = ItFixtures.institute(storage, "Institute");
        Department department = ItFixtures.department(storage, institute, "Department");
        Subdepartment subdepartment = ItFixtures.subdepartment(storage, department, "Subdepartment");
        Finance finance = ItFixtures.finance(storage, "F", "2024-01-01", "2024-12-31", "1000", "1000", "1000");
        FinanceDepartment financeDepartment = ItFixtures.financeDepartment(storage, finance, subdepartment, "1000", "1000", "1000");
        Cpv soya = storage.CPV.validateCode("03111100-3");
        Cpv peanuts = storage.CPV.validateCode("03111200-4");
        Cpv tobacco = storage.CPV.validateCode("03112000-9");
        Cpv office = storage.CPV.validateCode("30000000-9");
        // 0311 and 3000 are the 4-digit groups; the soya, soya-2 and peanuts bids share 0311 / MATERIALS
        ItFixtures.bid(storage, financeDepartment, BidType.MATERIALS, "10", 3, "soya", soya);
        ItFixtures.bid(storage, financeDepartment, BidType.MATERIALS, "2.50", 4, "peanuts", peanuts);
        ItFixtures.bid(storage, financeDepartment, BidType.EQUIPMENT, "100", 1, "tobacco", tobacco);
        ItFixtures.bid(storage, financeDepartment, BidType.EQUIPMENT, "7", 1, "office", office);
        ItFixtures.bid(storage, financeDepartment, BidType.MATERIALS, "1", 1, "soya-2", soya);
        Finance otherYear = ItFixtures.finance(storage, "G", "2020-01-01", "2020-12-31", "10", "10", "10");
        FinanceDepartment otherYearDepartment = ItFixtures.financeDepartment(storage, otherYear, subdepartment, "10", "10", "10");
        ItFixtures.bid(storage, otherYearDepartment, BidType.MATERIALS, "999", 1, "old", soya);

        // the lookup is the one the controller uses
        List<CpvAmount> result = CpvAmount.groupByCpvAndType(storage.BIDS.retrieve(new GetBidsQuery(2024)),
                cpvCode -> storage.CPV.get(new CpvRequestQuery(cpvCode, 0)).get(0));

        assertThat(result).hasSize(3);
        // sorted by type, then CPV code
        assertThat(result).extracting(CpvAmount::getType)
                .containsExactly(BidType.MATERIALS, BidType.EQUIPMENT, BidType.EQUIPMENT);
        assertThat(result).extracting(amount -> amount.getCpv().getCpvId())
                .containsExactly("03110000-5", "03110000-5", "30000000-9");
        assertThat(result.get(0).getTotalAmount()).isEqualByComparingTo("41");   // 10 * 3 + 2.50 * 4 + 1 * 1
        assertThat(result.get(0).getBids()).hasSize(3);
        assertThat(result.get(1).getTotalAmount()).isEqualByComparingTo("100");
        assertThat(result.get(2).getTotalAmount()).isEqualByComparingTo("7");
    }
}
