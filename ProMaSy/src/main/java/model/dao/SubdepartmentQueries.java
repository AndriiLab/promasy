package model.dao;

import model.models.SubdepartmentModel;
import model.models.SubdepartmentModel_;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class SubdepartmentQueries extends SQLQueries<SubdepartmentModel>{

    SubdepartmentQueries() {
        super(SubdepartmentModel.class);
    }

    public List<SubdepartmentModel> retrieve(long departmentId) throws SQLException {
        EntityManager em = Database.DB.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();
        Query query = em.createQuery("select sdm from SubdepartmentModel sdm join sdm.department where sdm.department.modelId = :departmentId and sdm.active = true");
        query.setParameter("departmentId", departmentId);
        List<SubdepartmentModel> list = query.getResultList();
        em.getTransaction().commit();
        em.close();

        return Collections.unmodifiableList(list);
    }

    @Override
    public List<SubdepartmentModel> getResults() throws SQLException {
        super.retrieve();
        criteriaQuery.where(criteriaBuilder.equal(root.get(SubdepartmentModel_.active), true));
        return super.getList();
    }
}
