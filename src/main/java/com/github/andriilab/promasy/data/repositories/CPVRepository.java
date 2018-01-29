package com.github.andriilab.promasy.data.repositories;

import com.github.andriilab.promasy.data.queries.cpv.CpvRequestQuery;
import com.github.andriilab.promasy.domain.EmptyModel;
import com.github.andriilab.promasy.domain.cpv.entities.Cpv;
import com.github.andriilab.promasy.domain.cpv.entities.Cpv_;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class CPVRepository {

    private List<Cpv> list = new LinkedList<>();
    private EntityManager entityManager;

    public CPVRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Cpv> get(CpvRequestQuery requestedCpvEvent) {
        String requestedCpv = requestedCpvEvent.getCpvRequest();

        list.clear();

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Cpv> criteria = cb.createQuery(Cpv.class);
        Root<Cpv> root = criteria.from(Cpv.class);
        criteria.select(root);

        //case: empty request
        if (requestedCpv.isEmpty()) {
            // selecting general Groups
            criteria.where(cb.equal(root.get(Cpv_.cpvLevel), 1));

            //case: first char is digit
        } else if (Character.isDigit(requestedCpv.charAt(0))) {
            CPVRequestObject reqObj = prepareDigitCpv(requestedCpv, requestedCpvEvent.getDepth());

            if (reqObj.getLvl() != 0) {
                criteria.where(cb.equal(root.get(Cpv_.cpvLevel), reqObj.getLvl()),
                        cb.like(root.get(Cpv_.cpvId), reqObj.getCpvRequest()));
            } else {
                criteria.where(cb.like(root.get(Cpv_.cpvId), reqObj.getCpvRequest()));
            }

            //case: first char is literal
        } else {
            //making lowercase regexp '%requestedcpv%'
            requestedCpv = "%" + requestedCpv.toLowerCase() + "%";
            //making lowercase like matching
            if (Character.UnicodeBlock.CYRILLIC.equals(Character.UnicodeBlock.of(requestedCpv.charAt(1)))) {
                criteria.where(cb.like(cb.lower(root.get(Cpv_.cpvUkr)), requestedCpv));
            } else {
                criteria.where(cb.like(cb.lower(root.get(Cpv_.cpvEng)), requestedCpv));
            }
        }

        //ascending sorting by cpv code
        criteria.orderBy(cb.asc(root.get(Cpv_.cpvId)));

        // getting only first 100 results
        list = entityManager.createQuery(criteria).setMaxResults(100).getResultList();

        return Collections.unmodifiableList(list);
    }

    // 0 - exact lvl, -1 - upper level, 1 - lower level
    private CPVRequestObject prepareDigitCpv(String requestedCpv, int depth) {
        // trim string if more than 8 char
        if (requestedCpv.length() > 8) {
            requestedCpv = requestedCpv.substring(0, 8);
        }

        if (depth == 0) { //if we want to see same level, i.e. direct search mode
            return new CPVRequestObject(requestedCpv, 0); // 0 - will search for every occurrence with any level
        }

        //else trim to first 0 value
        int zeroIndex = requestedCpv.indexOf("0", 2);
        if (zeroIndex != -1) {
            requestedCpv = requestedCpv.substring(0, zeroIndex);
        }

        // if one level up
        if (depth == -1) {
            if (requestedCpv.length() < 3) { // if request is shorter than 3 char return general groups
                return new CPVRequestObject(EmptyModel.STRING, 1);
            } else { //else trim last char
                requestedCpv = requestedCpv.substring(0, requestedCpv.length() - 1);
            }
        } //if one lvl down - do nothing

        return new CPVRequestObject(requestedCpv, determineLevel(requestedCpv) + 1);
    }

    private int determineLevel(String requestedCpv) {
        if (requestedCpv.length() < 3) {
            return 1;
        } else {
            return requestedCpv.length() - 1;
        }
    }

    public void create(Cpv model) {
        entityManager.getTransaction().begin();
        entityManager.persist(model);
        entityManager.getTransaction().commit();
    }

    public Cpv validateCode(String cpvCode) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Cpv> criteria = cb.createQuery(Cpv.class);
        Root<Cpv> root = criteria.from(Cpv.class);
        criteria.select(root);
        criteria.where(cb.equal(root.get(Cpv_.cpvId), cpvCode));
        List<Cpv> list = entityManager.createQuery(criteria).setMaxResults(100).getResultList();
        if (list.size() == 1) {
            return list.get(0);
        } else {
            return EmptyModel.CPV;
        }
    }

    private class CPVRequestObject {
        private final String cpvRequest;
        private final int lvl;

        CPVRequestObject(String cpvRequest, int lvl) {
            this.cpvRequest = cpvRequest + "%";
            this.lvl = lvl;
        }

        String getCpvRequest() {
            return cpvRequest;
        }

        int getLvl() {
            return lvl;
        }
    }
}
