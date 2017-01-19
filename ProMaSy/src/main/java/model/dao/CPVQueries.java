package model.dao;

import model.models.CPVModel;
import model.models.CPVModel_;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CPVQueries {

    private List<CPVModel> list = new ArrayList<>();

    public List<CPVModel> retrieve(String requestedCpv) throws SQLException {
        list.clear();

        EntityManager em = Database.DB.getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<CPVModel> criteria = cb.createQuery(CPVModel.class);
        Root<CPVModel> root = criteria.from(CPVModel.class);
        criteria.select(root);

        //case: empty request
        if (requestedCpv.isEmpty()) {
            // selecting general Groups
            requestedCpv = "__000000-_";

            criteria.where(cb.like(root.get(CPVModel_.cpvId), requestedCpv));
            criteria.where(cb.equal(root.get(CPVModel_.cpvLevel), 1));

            //case: first char is digit
        } else if (Character.isDigit(requestedCpv.charAt(0))) {
            if (requestedCpv.length() > 8) {
                // if more then 8 char, trim to 7 char and till non 0 value found
                requestedCpv = requestedCpv.substring(0, 8);
                while (requestedCpv.length() > 2 && requestedCpv.endsWith("0")) {
                    requestedCpv = requestedCpv.substring(0, requestedCpv.length() - 1);
                }
            }

            if (requestedCpv.length() < 2) {
                // selecting ParentGroups (level 1)
                requestedCpv = "0" + requestedCpv + "_00000-_";
            } else if (requestedCpv.length() < 3) {
                // selecting ParentGroups (level 1)
                requestedCpv = requestedCpv + "_00000-_";
            } else if (requestedCpv.length() < 4) {
                // selecting ParentClasses (level 2)
                requestedCpv = requestedCpv + "_0000-_";
            } else if (requestedCpv.length() < 5) {
                // selecting ParentCategories (level 3)
                requestedCpv = requestedCpv + "_000-_";
            } else if (requestedCpv.length() < 6) {
                // selecting exact Category (level 4)
                requestedCpv = requestedCpv + "_00-_";
            } else if (requestedCpv.length() < 7) {
                // selecting exact Category (level 5)
                requestedCpv = requestedCpv + "_0-_";
            } else if (requestedCpv.length() < 8) {
                // selecting exact Category (level 6)
                requestedCpv = requestedCpv + "_-_";
            } else if (requestedCpv.length() < 9) {
                // selecting exact Category (level 7)
                requestedCpv = requestedCpv + "-_";
            }

            criteria.where(cb.like(root.get(CPVModel_.cpvId), requestedCpv));

            //case: first char is literal
        } else {
            //making lowercase regexp '%requestedcpv%'
            requestedCpv = "%" + requestedCpv.toLowerCase() + "%";
            //making lowercase like matching
            if (Character.UnicodeBlock.CYRILLIC.equals(Character.UnicodeBlock.of(requestedCpv.charAt(1)))) {
                criteria.where(cb.like(cb.lower(root.get(CPVModel_.cpvUkr)), requestedCpv));
            } else {
                criteria.where(cb.like(cb.lower(root.get(CPVModel_.cpvEng)), requestedCpv));
            }
        }

        // getting only first 100 results
        list = em.createQuery(criteria).setMaxResults(100).getResultList();

        return Collections.unmodifiableList(list);
    }

    public void create(CPVModel model) throws SQLException {
        EntityManager entityManager = Database.DB.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(model);
        entityManager.getTransaction().commit();
    }
}
