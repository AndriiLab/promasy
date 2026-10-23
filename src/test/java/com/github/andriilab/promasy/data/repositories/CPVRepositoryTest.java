package com.github.andriilab.promasy.data.repositories;

import com.github.andriilab.promasy.data.queries.cpv.CpvRequestQuery;
import com.github.andriilab.promasy.domain.cpv.entities.Cpv;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CPVRepositoryTest {

    @Mock
    private EntityManager entityManager;
    @Mock(answer = Answers.RETURNS_SELF)
    private TypedQuery<Cpv> typedQuery;

    // depth: 0 - exact lvl, -1 - upper level, 1 - lower level
    @ParameterizedTest(name = "\"{0}\" depth {1} -> \"{2}\" level {3}")
    @CsvSource({
            // same level: only trimmed to 8 chars, level 0 means "any level"
            "03,0,03%,0",
            "031,0,031%,0",
            "0312345678901,0,03123456%,0",
            // one level down: cut at the first zero after the 2nd char, then level of that prefix + 1
            "03,1,03%,2",
            "03000000,1,03%,2",
            "03100000,1,031%,3",
            "0312,1,0312%,4",
            "03121234,1,03121234%,8",
            // one level up: cut at the first zero, drop the last char, level of that prefix + 1
            "03100000,-1,03%,2",
            "0312,-1,031%,3",
            "031,-1,03%,2",
            // too short to go up: every general group
            "03,-1,%,1",
            "0,-1,%,1"
    })
    void prepareDigitCpv_depthAndCode_buildsLikePatternAndLevel(String code, int depth, String pattern, int level) {
        CPVRepository.CPVRequestObject result = CPVRepository.prepareDigitCpv(code, depth);

        assertThat(result.getCpvRequest()).isEqualTo(pattern);
        assertThat(result.getLvl()).isEqualTo(level);
    }

    @ParameterizedTest(name = "\"{0}\" -> level {1}")
    @CsvSource({"'',1", "0,1", "03,1", "031,2", "0312,3", "03121234,7"})
    void determineLevel_codeLength_isLengthMinusOneButNeverBelowOne(String code, int level) {
        assertThat(CPVRepository.determineLevel(code)).isEqualTo(level);
    }

    @Test
    @SuppressWarnings("unchecked")
    void get_calledTwice_doesNotEmptyTheFirstResult() {
        CriteriaBuilder criteriaBuilder = mock(CriteriaBuilder.class);
        CriteriaQuery<Cpv> criteria = mock(CriteriaQuery.class);
        Root<Cpv> root = mock(Root.class);
        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(Cpv.class)).thenReturn(criteria);
        when(criteria.from(Cpv.class)).thenReturn(root);
        when(entityManager.createQuery(criteria)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(
                new ArrayList<>(Arrays.asList(new Cpv(), new Cpv())),
                new ArrayList<>(Arrays.asList(new Cpv())));
        CPVRepository repository = new CPVRepository(entityManager);

        List<Cpv> first = repository.get(new CpvRequestQuery("", 0));
        List<Cpv> second = repository.get(new CpvRequestQuery("", 0));

        assertThat(first).hasSize(2);
        assertThat(second).hasSize(1);
    }
}
