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

    public List<EmployeeModel> retrieve(Role role) throws SQLException {
        super.retrieve();
        criteriaQuery.where(criteriaBuilder.equal(root.get(EmployeeModel_.active), true));
        criteriaQuery.where(criteriaBuilder.equal(root.get(EmployeeModel_.role), role));
        return super.getList();
    }

    public List<EmployeeModel> retrieve(long departmentId) throws SQLException {
        super.retrieve();
        criteriaQuery.where(criteriaBuilder.equal(root.get(EmployeeModel_.active), true));
        criteriaQuery.where(criteriaBuilder.equal(root.get("subdepartment.department.modelId"), departmentId));
        return super.getList();
    }

    public List<EmployeeModel> retrieve(Role role, long departmentId) throws SQLException {
        super.retrieve();
        criteriaQuery.where(criteriaBuilder.equal(root.get(EmployeeModel_.active), true));
        criteriaQuery.where(criteriaBuilder.equal(root.get(EmployeeModel_.role), role));
        criteriaQuery.where(criteriaBuilder.equal(root.get("subdepartment.department.modelId"), departmentId));
        return super.getList();
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
        EntityManager em = Database.DB.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();
        Query query = em.createQuery("select count(em) from EmployeeModel em");
        int numberOfUsers = query.getFirstResult();
        em.getTransaction().commit();
        em.close();
        return numberOfUsers == 0;
    }

    @Override
    public List<EmployeeModel> getResults() throws SQLException {
        super.retrieve();
        criteriaQuery.where(criteriaBuilder.equal(root.get(EmployeeModel_.active), true));
        return super.getList();
    }
}
