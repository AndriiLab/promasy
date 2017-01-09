package model.dao;

import model.models.DepartmentModel;
import model.models.DepartmentModel_;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class DepartmentQueries extends SQLQueries<DepartmentModel>{

    DepartmentQueries() {
        super(DepartmentModel.class);
    }

    public List<DepartmentModel> retrieve(long instituteId) throws SQLException {
        EntityManager em = Database.DB.getEntityManager();
        em.getTransaction().begin();
        Query query = em.createQuery("select dm from DepartmentModel dm join dm.institute where dm.institute.modelId = :instituteId and dm.active = true");
        query.setParameter("instituteId", instituteId);
        List<DepartmentModel> list = query.getResultList();
        em.getTransaction().commit();

        return Collections.unmodifiableList(list);
    }

    @Override
    public List<DepartmentModel> getResults() throws SQLException {
        super.retrieve();
        criteriaQuery.where(criteriaBuilder.equal(root.get(DepartmentModel_.active), true));
        return super.getList();
    }
}
