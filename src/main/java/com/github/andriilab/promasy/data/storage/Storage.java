package com.github.andriilab.promasy.data.storage;

import com.github.andriilab.promasy.data.repositories.*;
import com.github.andriilab.promasy.domain.IEntity;

import javax.persistence.EntityManager;


@SuppressWarnings("unchecked")
public class Storage {
    public final CPVRepository CPV;
    public final AmountUnitRepository AMOUNTUNITS;
    public final InstituteRepository INSTITUTES;
    public final DepartmentRepository DEPARTMENTS;
    public final SubdepartmentRepository SUBDEPARTMENS;
    public final EmployeeRepository EMPLOYEES;
    public final ProducerRepository PRODUCERS;
    public final SupplierRepository SUPPLIERS;
    public final FinanceRepository FINANCES;
    public final FinanceDepartmentRepository DEPARTMENT_FINANCES;
    public final BidsRepository BIDS;
    public final BidStatusRepository BID_STATUSES;
    public final VersionRepository VERSIONS;
    public final ReasonsForSupplierChoiceRepository REASONS;
    public final RegistrationRepository REGISTRATION;

    private final IRepository<? extends IEntity>[] repositories;

    public Storage(EntityManager entityManager) {
        CPV = new CPVRepository(entityManager);
        AMOUNTUNITS = new AmountUnitRepository(entityManager);
        INSTITUTES = new InstituteRepository(entityManager);
        DEPARTMENTS = new DepartmentRepository(entityManager);
        SUBDEPARTMENS = new SubdepartmentRepository(entityManager);
        EMPLOYEES = new EmployeeRepository(entityManager);
        PRODUCERS = new ProducerRepository(entityManager);
        SUPPLIERS = new SupplierRepository(entityManager);
        FINANCES = new FinanceRepository(entityManager);
        DEPARTMENT_FINANCES = new FinanceDepartmentRepository(entityManager);
        BIDS = new BidsRepository(entityManager);
        BID_STATUSES = new BidStatusRepository(entityManager);
        VERSIONS = new VersionRepository(entityManager);
        REASONS = new ReasonsForSupplierChoiceRepository(entityManager);
        REGISTRATION = new RegistrationRepository(entityManager);

        repositories = new IRepository[]{
                AMOUNTUNITS, INSTITUTES, DEPARTMENTS, SUBDEPARTMENS,
                EMPLOYEES, PRODUCERS, SUPPLIERS, FINANCES,
                DEPARTMENT_FINANCES, BIDS, BID_STATUSES, REASONS
        };
    }

    public <E extends IEntity> IRepository<E> getByClass(Class<E> entityClass) {
        for (IRepository<?> repository : repositories) {
            if (repository.getEntityClass().equals(entityClass)) {
                return (IRepository<E>) repository;
            }
        }

        throw new IllegalArgumentException("No repository for entity class " + entityClass.getName());
    }
}
