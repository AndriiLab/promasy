package model.dao;

import model.enums.Role;
import model.models.EmployeeModel;
import model.models.EmployeeModel_;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.sql.SQLException;
import java.util.List;

public class EmployeeQueries extends SQLQueries<EmployeeModel> {

    EmployeeQueries() {
        super(EmployeeModel.class);
    }

    @Override
    public List<EmployeeModel> getResults() throws SQLException {
        super.retrieve();
        //show all employees
//        criteriaQuery.where(criteriaBuilder.equal(root.get(EmployeeModel_.active), true));
        criteriaQuery.orderBy(criteriaBuilder.asc(root.get(EmployeeModel_.empLName)), criteriaBuilder.asc(root.get(EmployeeModel_.empFName)));
        return super.getList();
    }

    public List<EmployeeModel> retrieve(Role role) throws SQLException {
        super.retrieve();
        criteriaQuery.where(criteriaBuilder.equal(root.get(EmployeeModel_.active), true));
        criteriaQuery.where(criteriaBuilder.equal(root.get(EmployeeModel_.role), role));
        criteriaQuery.orderBy(criteriaBuilder.asc(root.get(EmployeeModel_.empLName)), criteriaBuilder.asc(root.get(EmployeeModel_.empFName)));
        return super.getList();
    }

    public List<EmployeeModel> retrieve(long departmentId) throws SQLException {
        EntityManager em = Database.DB.getEntityManager();
        em.getTransaction().begin();
        Query query = em.createQuery("select em from EmployeeModel em where em.active = true and em.subdepartment.department.modelId = :departmentId order by em.empLName, em.empFName")
                .setParameter("departmentId", departmentId);
        List<EmployeeModel> list = (List<EmployeeModel>) query.getResultList();
        em.getTransaction().commit();

        return list;
    }

    public List<EmployeeModel> retrieve(Role role, long departmentId) throws SQLException {
        EntityManager em = Database.DB.getEntityManager();
        em.getTransaction().begin();
        Query query = em.createQuery("select em from EmployeeModel em where em.active = true and em.subdepartment.department.modelId = :departmentId and em.role = :empRole order by em.empLName, em.empFName")
                .setParameter("empRole", role)
                .setParameter("departmentId", departmentId);
        List<EmployeeModel> list = (List<EmployeeModel>) query.getResultList();
        em.getTransaction().commit();

        return list;
    }

    public long getSalt(String username) throws SQLException {
        super.retrieve();
        criteriaQuery.where(criteriaBuilder.equal(root.get(EmployeeModel_.active), true));
        criteriaQuery.where(criteriaBuilder.equal(root.get(EmployeeModel_.login), username));
        List<EmployeeModel> empList = super.getList();

        if (empList.isEmpty()) {
            return 0;
        }

        EmployeeModel employee = empList.get(0);
        return employee.getSalt();
    }

    public boolean checkLogin(String username, String password) throws SQLException {
        super.retrieve();
        criteriaQuery.where(criteriaBuilder.equal(root.get(EmployeeModel_.active), true));
        criteriaQuery.where(criteriaBuilder.equal(root.get(EmployeeModel_.login), username));
        criteriaQuery.where(criteriaBuilder.equal(root.get(EmployeeModel_.password), password));
        List<EmployeeModel> empList = super.getList();

        if (empList.isEmpty()) {
            return false;
        }

        LoginData.getInstance(empList.get(0));
        return true;
    }

    // if username doesn't exist - return true
    public boolean checkLogin(String username) throws SQLException {
        super.retrieve();
        criteriaQuery.where(criteriaBuilder.equal(root.get(EmployeeModel_.login), username));
        return super.getList().isEmpty();
    }

    //if no users exist - return true
    public boolean isFirstRun() throws SQLException {
        EntityManager em = Database.DB.getEntityManager();
        em.getTransaction().begin();
        Query query = em.createQuery("select count(em) from EmployeeModel em where em.login is not null");
        long numberOfUsers = (long) query.getSingleResult();
        em.getTransaction().commit();
        return numberOfUsers == 0;
    }

    public EmployeeModel getUserWithId(long id) {
        EntityManager em = Database.DB.getEntityManager();
        em.getTransaction().begin();
        EmployeeModel model = em.find(EmployeeModel.class, id);
        em.getTransaction().commit();

        return model;
    }
}
