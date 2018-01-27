package com.github.andriilab.promasy.data.queries;

import com.github.andriilab.promasy.data.storage.Database;
import com.github.andriilab.promasy.domain.organization.entities.Department;
import com.github.andriilab.promasy.domain.organization.entities.Department_;
import org.hibernate.JDBCException;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;

public class DepartmentQueries extends SQLQueries<Department> {

    public DepartmentQueries() {
        super(Department.class);
    }

    public List<Department> retrieve(long instituteId) {
        EntityManager em = Database.CONNECTOR.getEntityManager();
        em.getTransaction().begin();
        TypedQuery<Department> query = em.createQuery("select dm from Department dm join dm.institute where dm.institute.modelId = :instituteId and dm.active = true order by dm.depName",
                Department.class);
        query.setParameter("instituteId", instituteId);
        List<Department> list = query.getResultList();
        em.getTransaction().commit();

        return Collections.unmodifiableList(list);
    }

    @Override
    public List<Department> getResults() throws JDBCException {
        super.retrieve();
        criteriaQuery.where(criteriaBuilder.equal(root.get(Department_.active), true));
        criteriaQuery.orderBy(criteriaBuilder.asc(root.get(Department_.depName)));
        return super.getList();
    }
}
