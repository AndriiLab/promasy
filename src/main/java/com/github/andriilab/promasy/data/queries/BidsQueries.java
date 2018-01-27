package com.github.andriilab.promasy.data.queries;

import com.github.andriilab.promasy.data.storage.Database;
import com.github.andriilab.promasy.domain.bid.entities.Bid;
import com.github.andriilab.promasy.domain.bid.entities.Bid_;
import com.github.andriilab.promasy.domain.bid.entities.CpvAmount;
import com.github.andriilab.promasy.domain.bid.enums.BidType;
import com.github.andriilab.promasy.domain.cpv.entities.Cpv;
import com.github.andriilab.promasy.domain.finance.entities.FinanceDepartment;
import com.github.andriilab.promasy.domain.organization.entities.Department;
import com.github.andriilab.promasy.domain.organization.entities.Subdepartment;
import com.github.andriilab.promasy.presentation.cpv.CpvRequestContainer;
import org.hibernate.JDBCException;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * CRUD for bid data
 */
public class BidsQueries extends SQLQueries<Bid> {

    public BidsQueries() {
        super(Bid.class);
    }

    public List<Bid> retrieve(BidType type, FinanceDepartment financeDepartment) {
        EntityManager em = Database.CONNECTOR.getEntityManager();
        em.getTransaction().begin();
        TypedQuery<Bid> query = em.createQuery("select bm from Bid bm where bm.active = true and bm.type = :bidType and bm.finances = :financeDepartment order by coalesce(bm.modifiedDate, bm.createdDate) desc",
                Bid.class)
                .setParameter("bidType", type)
                .setParameter("financeDepartment", financeDepartment);
        List<Bid> list = query.getResultList();
        em.getTransaction().commit();

        list.forEach(em::refresh);

        return list;
    }

    public List<Bid> retrieve(BidType type, Department department) {
        EntityManager em = Database.CONNECTOR.getEntityManager();
        em.getTransaction().begin();
        TypedQuery<Bid> query = em.createQuery("select bm from Bid bm where bm.active = true and bm.type = :bidType and bm.finances.subdepartment.department = :department order by coalesce(bm.modifiedDate, bm.createdDate) desc",
                Bid.class)
                .setParameter("bidType", type)
                .setParameter("department", department);
        List<Bid> list = query.getResultList();
        em.getTransaction().commit();

        list.forEach(em::refresh);

        return list;
    }

    public List<Bid> retrieve(BidType type, Subdepartment subdepartment) {
        EntityManager em = Database.CONNECTOR.getEntityManager();
        em.getTransaction().begin();
        TypedQuery<Bid> query = em.createQuery("select bm from Bid bm where bm.active = true and bm.type = :bidType and bm.finances.subdepartment = :subdepartment order by coalesce(bm.modifiedDate, bm.createdDate) desc",
                Bid.class)
                .setParameter("bidType", type)
                .setParameter("subdepartment", subdepartment);
        List<Bid> list = query.getResultList();
        em.getTransaction().commit();

        list.forEach(em::refresh);

        return list;
    }

    public List<Bid> getResults(BidType type) throws JDBCException {
        super.retrieve();
        criteriaQuery.where(criteriaBuilder.equal(root.get(Bid_.active), true));
        criteriaQuery.where(criteriaBuilder.equal(root.get(Bid_.type), type));
        criteriaQuery.orderBy(criteriaBuilder.desc(criteriaBuilder.coalesce(root.get(Bid_.modifiedDate), root.get(Bid_.createdDate))));

        List<Bid> list = super.getList();
        list.forEach(Database.CONNECTOR.getEntityManager()::refresh);
        return list;
    }

    @Override
    public List<Bid> getResults() throws JDBCException {
        super.retrieve();
        criteriaQuery.where(criteriaBuilder.equal(root.get(Bid_.active), true));
        return super.getList();
    }

    public BigDecimal getTotalAmount(FinanceDepartment financeDepartmentModel, BidType bidType) {
        EntityManager em = Database.CONNECTOR.getEntityManager();
        em.getTransaction().begin();
        Query query = em.createQuery("select sum(bm.amount * bm.onePrice) from Bid bm where bm.active = true and bm.type = :bidType and bm.finances = :finances")
                .setParameter("bidType", bidType)
                .setParameter("finances", financeDepartmentModel);
        BigDecimal totalAmount = (BigDecimal) query.getSingleResult();
        em.getTransaction().commit();

        if (totalAmount == null) {
            totalAmount = BigDecimal.ZERO;
        }

        return totalAmount;
    }

    public List<CpvAmount> getCpvAmount() throws JDBCException {
        List<Bid> bidModels = getResults();
        Map<String, CpvAmount> map = new HashMap<>();

        for (Bid bidModel : bidModels) {
            String cpv = bidModel.getCpv().getCpvId().substring(0, 4);
            BidType type = bidModel.getType();
            BigDecimal bidAmount = bidModel.getTotalPrice();

            String key = cpv + "0000 " + type.toString();
            if (map.containsKey(key)) {
                CpvAmount cpvAmount = map.get(key);
                cpvAmount.addBidModel(bidModel);
                cpvAmount.addToTotalAmount(bidAmount);
                map.put(key, cpvAmount);
            } else {
                Cpv fourDigitCpv = Database.CPV.retrieve(new CpvRequestContainer(key, 0)).get(0);
                map.put(key, new CpvAmount(fourDigitCpv, type, bidAmount, bidModel));
            }
        }

        return Collections.unmodifiableList(map.values().stream()
                .sorted(Comparator.comparing(CpvAmount::getType)
                        .thenComparing(Comparator.comparing(m -> m.getCpv().getCpvId())))
                .collect(Collectors.toList()));
    }
}
