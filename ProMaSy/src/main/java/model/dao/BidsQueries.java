package model.dao;

import model.enums.BidType;
import model.models.*;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.*;

/**
 * CRUD for bid data
 */
public class BidsQueries extends SQLQueries<BidModel> {

    BidsQueries() {
        super(BidModel.class);
    }

    public List<BidModel> retrieve(BidType type, FinanceDepartmentModel financeDepartment) throws SQLException {
        EntityManager em = Database.DB.getEntityManager();
        em.getTransaction().begin();
        Query query = em.createQuery("select bm from BidModel bm where bm.active = true and bm.type = :bidType and bm.finances = :financeDepartment order by coalesce(bm.modifiedDate, bm.createdDate) desc")
                .setParameter("bidType", type)
                .setParameter("financeDepartment", financeDepartment);
        List<BidModel> list = (List<BidModel>) query.getResultList();
        em.getTransaction().commit();

        list.forEach(em::refresh);

        return list;
    }

    public List<BidModel> retrieve(BidType type, DepartmentModel department) throws SQLException {
        EntityManager em = Database.DB.getEntityManager();
        em.getTransaction().begin();
        Query query = em.createQuery("select bm from BidModel bm where bm.active = true and bm.type = :bidType and bm.finances.subdepartment.department = :department order by coalesce(bm.modifiedDate, bm.createdDate) desc")
                .setParameter("bidType", type)
                .setParameter("department", department);
        List<BidModel> list = (List<BidModel>) query.getResultList();
        em.getTransaction().commit();

        list.forEach(em::refresh);

        return list;
    }

    public List<BidModel> retrieve(BidType type, SubdepartmentModel subdepartment) throws SQLException {
        EntityManager em = Database.DB.getEntityManager();
        em.getTransaction().begin();
        Query query = em.createQuery("select bm from BidModel bm where bm.active = true and bm.type = :bidType and bm.finances.subdepartment = :subdepartment order by coalesce(bm.modifiedDate, bm.createdDate) desc")
                .setParameter("bidType", type)
                .setParameter("subdepartment", subdepartment);
        List<BidModel> list = (List<BidModel>) query.getResultList();
        em.getTransaction().commit();

        list.forEach(em::refresh);

        return list;
    }

    public List<BidModel> getResults(BidType type) throws SQLException {
        super.retrieve();
        criteriaQuery.where(criteriaBuilder.equal(root.get(BidModel_.active), true));
        criteriaQuery.where(criteriaBuilder.equal(root.get(BidModel_.type), type));
        criteriaQuery.orderBy(criteriaBuilder.desc(criteriaBuilder.coalesce(root.get(BidModel_.modifiedDate), root.get(BidModel_.createdDate))));

        List<BidModel> list = super.getList();
        list.forEach(Database.DB.getEntityManager()::refresh);
        return list;
    }

    @Override
    public List<BidModel> getResults() throws SQLException {
        super.retrieve();
        criteriaQuery.where(criteriaBuilder.equal(root.get(BidModel_.active), true));
        return super.getList();
    }

    public BigDecimal getTotalAmount(FinanceDepartmentModel financeDepartmentModel, BidType bidType) {
        EntityManager em = Database.DB.getEntityManager();
        em.getTransaction().begin();
        Query query = em.createQuery("select sum(bm.amount * bm.onePrice) from BidModel bm where bm.active = true and bm.type = :bidType and bm.finances = :finances")
                .setParameter("bidType", bidType)
                .setParameter("finances", financeDepartmentModel);
        BigDecimal totalAmount = (BigDecimal) query.getSingleResult();
        em.getTransaction().commit();

        if (totalAmount == null) {
            totalAmount = BigDecimal.ZERO;
        }

        return totalAmount;
    }

    public List<CpvAmountModel> getCpvAmount() throws SQLException {
        List<BidModel> bidModels = getResults();
        Map<String, CpvAmountModel> map = new HashMap<>();

        for (BidModel bidModel : bidModels) {
            String cpv = bidModel.getCpv().getCpvId().substring(0, 4);
            BidType type = bidModel.getType();
            BigDecimal bidAmount = bidModel.getTotalPrice();

            String key = cpv + "0000 " + type.toString();
            if (map.containsKey(key)) {
                CpvAmountModel cpvAmountModel = map.get(key);
                cpvAmountModel.addBidModel(bidModel);
                cpvAmountModel.addToTotalAmount(bidAmount);
                map.put(key, cpvAmountModel);
            } else {
                CPVModel fourDigitCpv = Database.CPV.retrieve(key).get(0);
                map.put(key, new CpvAmountModel(fourDigitCpv, type, bidAmount, bidModel));
            }
        }
        List<CpvAmountModel> cpvAmountModels = new ArrayList<>(map.values());
        cpvAmountModels.sort(Comparator.comparing(CpvAmountModel::getType).thenComparing(Comparator.comparing(m -> m.getCpv().getCpvId())));

        return Collections.unmodifiableList(cpvAmountModels);
    }
}
