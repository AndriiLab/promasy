package com.github.andriilab.promasy.data.repositories;

import com.github.andriilab.promasy.domain.IEntity;
import com.github.andriilab.promasy.domain.organization.entities.Department;
import com.github.andriilab.promasy.domain.organization.entities.Institute;
import com.github.andriilab.promasy.domain.organization.entities.Subdepartment;
import com.github.andriilab.promasy.it.AbstractIntegrationTest;
import com.github.andriilab.promasy.it.ItFixtures;
import com.github.andriilab.promasy.it.Sql;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * The institute, department and subdepartment hierarchy: the by-parent queries, the active filter, the ordering and the
 * soft delete that walks down the tree.
 */
class OrganizationRepositoryIT extends AbstractIntegrationTest {

    private static List<String> names(List<? extends IEntity> entities) {
        return entities.stream().map(IEntity::getDescription).collect(Collectors.toList());
    }

    @Test
    void getDepartments_byInstitute_returnsOnlyActiveChildrenOfThatInstituteOrderedByName() {
        Institute institute = ItFixtures.institute(storage, "Institute");
        Institute other = ItFixtures.institute(storage, "Other institute");
        ItFixtures.department(storage, institute, "B");
        ItFixtures.department(storage, institute, "A");
        Department removed = ItFixtures.department(storage, institute, "C");
        ItFixtures.department(storage, other, "Foreign");
        storage.DEPARTMENTS.delete(removed);

        List<Department> departments = storage.DEPARTMENTS.get(institute.getModelId());

        assertThat(names(departments)).containsExactly("A", "B");
    }

    @Test
    void getDepartments_unknownInstitute_returnsEmptyList() {
        ItFixtures.department(storage, ItFixtures.institute(storage, "Institute"), "A");

        assertThat(storage.DEPARTMENTS.get(Long.MAX_VALUE)).isEmpty();
    }

    @Test
    void getSubdepartments_byDepartment_returnsOnlyActiveChildrenOfThatDepartmentOrderedByName() {
        Institute institute = ItFixtures.institute(storage, "Institute");
        Department department = ItFixtures.department(storage, institute, "Department");
        Department other = ItFixtures.department(storage, institute, "Other department");
        ItFixtures.subdepartment(storage, department, "B");
        ItFixtures.subdepartment(storage, department, "A");
        Subdepartment removed = ItFixtures.subdepartment(storage, department, "C");
        ItFixtures.subdepartment(storage, other, "Foreign");
        storage.SUBDEPARTMENS.delete(removed);

        List<Subdepartment> subdepartments = storage.SUBDEPARTMENS.get(department.getModelId());

        assertThat(names(subdepartments)).containsExactly("A", "B");
    }

    @Test
    void getResults_allLevels_returnOnlyActiveEntitiesOrderedByName() {
        Institute second = ItFixtures.institute(storage, "b-institute");
        Institute first = ItFixtures.institute(storage, "a-institute");
        Institute removedInstitute = ItFixtures.institute(storage, "removed-institute");
        Department department = ItFixtures.department(storage, first, "b-department");
        ItFixtures.department(storage, second, "a-department");
        Department removedDepartment = ItFixtures.department(storage, first, "removed-department");
        ItFixtures.subdepartment(storage, department, "b-subdepartment");
        ItFixtures.subdepartment(storage, department, "a-subdepartment");
        Subdepartment removedSubdepartment = ItFixtures.subdepartment(storage, department, "removed-subdepartment");
        storage.INSTITUTES.delete(removedInstitute);
        storage.DEPARTMENTS.delete(removedDepartment);
        storage.SUBDEPARTMENS.delete(removedSubdepartment);

        assertThat(names(storage.INSTITUTES.getResults())).containsExactly("a-institute", "b-institute");
        assertThat(names(storage.DEPARTMENTS.getResults())).containsExactly("a-department", "b-department");
        assertThat(names(storage.SUBDEPARTMENS.getResults())).containsExactly("a-subdepartment", "b-subdepartment");
    }

    @Test
    void deleteInstitute_withDepartmentsAndSubdepartments_deactivatesTheWholeBranch() {
        Institute institute = ItFixtures.institute(storage, "Institute");
        Department department = ItFixtures.department(storage, institute, "Department");
        Subdepartment subdepartment = ItFixtures.subdepartment(storage, department, "Subdepartment");
        Institute untouched = ItFixtures.institute(storage, "Untouched");
        Department untouchedDepartment = ItFixtures.department(storage, untouched, "Untouched department");

        storage.INSTITUTES.delete(institute);

        assertThat(Sql.scalar("SELECT active FROM promasy.institutes WHERE id = ?", institute.getModelId()))
                .isEqualTo(false);
        assertThat(Sql.scalar("SELECT active FROM promasy.departments WHERE id = ?", department.getModelId()))
                .isEqualTo(false);
        assertThat(Sql.scalar("SELECT active FROM promasy.subdepartments WHERE id = ?", subdepartment.getModelId()))
                .isEqualTo(false);
        assertThat(Sql.scalar("SELECT modified_by FROM promasy.subdepartments WHERE id = ?",
                subdepartment.getModelId())).isEqualTo(admin.getModelId());
        assertThat(names(storage.DEPARTMENTS.getResults())).containsExactly(untouchedDepartment.getDescription());
        assertThat(storage.DEPARTMENTS.get(institute.getModelId())).isEmpty();
        assertThat(storage.SUBDEPARTMENS.get(department.getModelId())).isEmpty();
    }

    @Test
    void create_departmentWithInstitute_persistsForeignKeys() {
        Institute institute = ItFixtures.institute(storage, "Institute");
        Department department = ItFixtures.department(storage, institute, "Department");
        Subdepartment subdepartment = ItFixtures.subdepartment(storage, department, "Subdepartment");

        assertThat(Sql.scalar("SELECT inst_id FROM promasy.departments WHERE id = ?", department.getModelId()))
                .isEqualTo(institute.getModelId());
        assertThat(Sql.scalar("SELECT dep_id FROM promasy.subdepartments WHERE id = ?", subdepartment.getModelId()))
                .isEqualTo(department.getModelId());
    }
}
