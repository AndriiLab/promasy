package com.github.andriilab.promasy.data.queries;

import com.github.andriilab.promasy.data.controller.LoginData;
import com.github.andriilab.promasy.data.storage.Database;
import com.github.andriilab.promasy.domain.organization.entities.Employee;
import com.github.andriilab.promasy.domain.organization.entities.Employee_;
import com.github.andriilab.promasy.domain.organization.enums.Role;
import org.hibernate.JDBCException;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

public class EmployeeQueries extends SQLQueries<Employee> {

    public EmployeeQueries() {
        super(Employee.class);
    }

    @Override
    public List<Employee> getResults() throws JDBCException {
        super.retrieve();
        //show all employees
//        criteriaQuery.where(criteriaBuilder.equal(root.get(Employee_.active), true));
        criteriaQuery.orderBy(criteriaBuilder.asc(root.get(Employee_.empLName)), criteriaBuilder.asc(root.get(Employee_.empFName)));
        return super.getList();
    }

    public List<Employee> retrieve(Role role) throws JDBCException {
        super.retrieve();
        criteriaQuery.where(criteriaBuilder.equal(root.get(Employee_.active), true));
        criteriaQuery.where(criteriaBuilder.equal(root.get(Employee_.role), role));
        criteriaQuery.orderBy(criteriaBuilder.asc(root.get(Employee_.empLName)), criteriaBuilder.asc(root.get(Employee_.empFName)));
        return super.getList();
    }

    public List<Employee> retrieve(long departmentId) {
        EntityManager em = Database.CONNECTOR.getEntityManager();
        em.getTransaction().begin();
        TypedQuery<Employee> query = em.createQuery("select em from Employee em where em.active = true and em.subdepartment.department.modelId = :departmentId order by em.empLName, em.empFName", Employee.class)
                .setParameter("departmentId", departmentId);
        List<Employee> list = query.getResultList();
        em.getTransaction().commit();

        return list;
    }

    public List<Employee> retrieve(Role role, long departmentId) {
        EntityManager em = Database.CONNECTOR.getEntityManager();
        em.getTransaction().begin();
        TypedQuery<Employee> query = em.createQuery("select em from Employee em where em.active = true and em.subdepartment.department.modelId = :departmentId and em.role = :empRole order by em.empLName, em.empFName", Employee.class)
                .setParameter("empRole", role)
                .setParameter("departmentId", departmentId);
        List<Employee> list = query.getResultList();
        em.getTransaction().commit();

        return list;
    }

    public long getSalt(String username) throws JDBCException {
        super.retrieve();
        criteriaQuery.where(criteriaBuilder.equal(root.get(Employee_.active), true));
        criteriaQuery.where(criteriaBuilder.equal(root.get(Employee_.login), username));
        List<Employee> empList = super.getList();

        if (empList.isEmpty()) {
            return 0;
        }

        Employee employee = empList.get(0);
        return employee.getSalt();
    }

    public boolean checkLogin(String username, String password) throws JDBCException {
        super.retrieve();
        criteriaQuery.where(criteriaBuilder.equal(root.get(Employee_.active), true));
        criteriaQuery.where(criteriaBuilder.equal(root.get(Employee_.login), username));
        criteriaQuery.where(criteriaBuilder.equal(root.get(Employee_.password), password));
        List<Employee> empList = super.getList();

        if (empList.isEmpty()) {
            return false;
        }

        LoginData.getInstance(empList.get(0));
        return true;
    }

    // if username doesn't exist - return true
    public boolean checkLogin(String username) throws JDBCException {
        super.retrieve();
        criteriaQuery.where(criteriaBuilder.equal(root.get(Employee_.login), username));
        return super.getList().isEmpty();
    }

    //if no users exist - return true
    public boolean isFirstRun() {
        EntityManager em = Database.CONNECTOR.getEntityManager();
        em.getTransaction().begin();
        Query query = em.createQuery("select count(em) from Employee em where em.login is not null");
        long numberOfUsers = (long) query.getSingleResult();
        em.getTransaction().commit();
        return numberOfUsers == 0;
    }

    public Employee getUserWithId(long id) {
        EntityManager em = Database.CONNECTOR.getEntityManager();
        em.getTransaction().begin();
        Employee model = em.find(Employee.class, id);
        em.getTransaction().commit();

        return model;
    }
}
