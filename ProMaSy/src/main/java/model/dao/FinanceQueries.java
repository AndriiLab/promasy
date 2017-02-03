package model.dao;

import model.models.FinanceModel;
import model.models.FinanceModel_;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

/**
 * CRUD class for finance data
 */
public class FinanceQueries extends SQLQueries<FinanceModel> {

    FinanceQueries() {
        super(FinanceModel.class);
    }

    public List<FinanceModel> retrieve(long departmentId) throws SQLException {
        super.retrieve();
        criteriaQuery.where(criteriaBuilder.equal(root.get(FinanceModel_.active), true));
        criteriaQuery.where(criteriaBuilder.equal(root.get("department.modelId"), departmentId));
        return super.getList();
    }

    public List<FinanceModel> retrieveByDepartmentId(long departmentId) throws SQLException {
        EntityManager em = Database.DB.getEntityManager();
        em.getTransaction().begin();
        Query query = em.createQuery("select fm from FinanceModel fm join fm.financeDepartmentModels fdm where fdm.subdepartment.department.modelId = :departmentId and fm.active = true");
        query.setParameter("departmentId", departmentId);
        List<FinanceModel> list = query.getResultList();
        em.getTransaction().commit();

        return Collections.unmodifiableList(list);
    }

    @Override
    public List<FinanceModel> getResults() throws SQLException {
        super.retrieve();
        criteriaQuery.where(criteriaBuilder.equal(root.get(FinanceModel_.active), true));
        return super.getList();
    }
}
