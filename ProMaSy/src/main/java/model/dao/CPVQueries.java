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

    public List<CPVModel> retrieve(String requestedCpv, boolean sameLvlShow) throws SQLException {
        list.clear();

        EntityManager em = Database.DB.getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<CPVModel> criteria = cb.createQuery(CPVModel.class);
        Root<CPVModel> root = criteria.from(CPVModel.class);
        criteria.select(root);

        int level = 0;

        //case: empty request
        if (requestedCpv.isEmpty()) {
            // selecting general Divisions
            requestedCpv = "__000000-_";

            criteria.where(cb.like(root.get(CPVModel_.cpvId), requestedCpv));
            criteria.where(cb.greaterThan(root.get(CPVModel_.cpvLevel), level));

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
                // selecting ParentGroups
                requestedCpv = "0" + requestedCpv + "_00000-_";
                level = 1;
            } else if (requestedCpv.length() < 3) {
                // selecting ParentGroups
                requestedCpv = requestedCpv + "_00000-_";
                level = 1;
            } else if (requestedCpv.length() < 4) {
                // selecting ParentClasses
                requestedCpv = requestedCpv + "_0000-_";
                level = 2;
            } else if (requestedCpv.length() < 5) {
                // selecting ParentCategories
                requestedCpv = requestedCpv + "_000-_";
                level = 3;
            } else if (requestedCpv.length() < 6) {
                // selecting exact Category
                requestedCpv = requestedCpv + "_00-_";
                level = 4;
            } else if (requestedCpv.length() < 7) {
                // selecting exact Category
                requestedCpv = requestedCpv + "_0-_";
                level = 5;
            } else if (requestedCpv.length() < 8) {
                // selecting exact Category
                requestedCpv = requestedCpv + "_-_";
                level = 6;
            } else if (requestedCpv.length() < 9) {
                // selecting exact Category
                requestedCpv = requestedCpv + "-_";
                level = 7;
            }
            if (sameLvlShow) {
                level--;
            }

            criteria.where(cb.like(root.get("cpvId"), requestedCpv));
            criteria.where(cb.greaterThan(root.get("cpvLevel"), level));

            //case: first char is literal
        } else {
            requestedCpv = "%" + requestedCpv + "%";
            if (Character.UnicodeBlock.CYRILLIC.equals(Character.UnicodeBlock.of(requestedCpv.charAt(1)))) {
                criteria.where(cb.like(root.get(CPVModel_.cpvUkr), requestedCpv));
            } else {
                criteria.where(cb.like(root.get(CPVModel_.cpvEng), requestedCpv));
            }
        }

        list = em.createQuery(criteria).getResultList();

        return Collections.unmodifiableList(list);
    }
}
