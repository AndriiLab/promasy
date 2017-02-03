package model.dao;

import model.enums.BidType;
import model.models.FinanceDepartmentModel;
import model.models.FinanceDepartmentModel_;
import model.models.FinanceModel;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

/**
 * CRUD for {@link FinanceDepartmentModel}
 */
public class FinanceDepartmentQueries extends SQLQueries<FinanceDepartmentModel> {

    FinanceDepartmentQueries() {
        super(FinanceDepartmentModel.class);
    }

    public List<FinanceDepartmentModel> retrieveByFinanceId(long financeID) throws SQLException {
        EntityManager em = Database.DB.getEntityManager();
        em.getTransaction().begin();
        Query query = em.createQuery("select fdm from FinanceDepartmentModel fdm join fdm.finances where fdm.finances.modelId = :financeId and fdm.active = true");
        query.setParameter("financeId", financeID);
        List<FinanceDepartmentModel> list = query.getResultList();
        em.getTransaction().commit();

        return Collections.unmodifiableList(list);
    }

    public List<FinanceDepartmentModel> retrieveByDepartmentId(long departmentId) throws SQLException {
        EntityManager em = Database.DB.getEntityManager();
        em.getTransaction().begin();
        Query query = em.createQuery("select fdm from FinanceDepartmentModel fdm join fdm.subdepartment.department where fdm.subdepartment.department.modelId = :departmentId and fdm.active = true");
        query.setParameter("departmentId", departmentId);
        List<FinanceDepartmentModel> list = query.getResultList();
        em.getTransaction().commit();

        return Collections.unmodifiableList(list);
    }

    @Override
    public List<FinanceDepartmentModel> getResults() throws SQLException {
        super.retrieve();
        criteriaQuery.where(criteriaBuilder.equal(root.get(FinanceDepartmentModel_.active), true));
        return super.getList();
    }

    public BigDecimal getTotalAmount(FinanceModel model, BidType type) {
        EntityManager em = Database.DB.getEntityManager();
        em.getTransaction().begin();
        Query query;
        switch (type) {
            case MATERIALS:
                query = em.createQuery("select sum(dfm.totalMaterialsAmount) from FinanceDepartmentModel dfm where dfm.active = true and dfm.finances = :finances");
                break;
            case EQUIPMENT:
                query = em.createQuery("select sum(dfm.totalEquipmentAmount) from FinanceDepartmentModel dfm where dfm.active = true and dfm.finances = :finances");
                break;
            case SERVICES:
                query = em.createQuery("select sum(dfm.totalServicesAmount) from FinanceDepartmentModel dfm where dfm.active = true and dfm.finances = :finances");
                break;
            default:
                return BigDecimal.ZERO;
        }
        query.setParameter("finances", model);
        BigDecimal totalAmount = (BigDecimal) query.getSingleResult();
        em.getTransaction().commit();

        if (totalAmount == null) {
            totalAmount = BigDecimal.ZERO;
        }

        return totalAmount;
    }
}
