package model.dao;

import model.enums.BidType;
import model.models.BidModel;
import model.models.DepartmentModel;
import model.models.FinanceDepartmentModel;

import javax.persistence.EntityManager;
import java.sql.SQLException;
import java.util.List;

/**
 * CRUD for bid data
 */
public class BidsQueries extends SQLQueries<BidModel>{

    BidsQueries() {
        super(BidModel.class);
    }

    public List<BidModel> retrieve(DepartmentModel department, FinanceDepartmentModel financeDepartment, BidType type) throws SQLException {
        super.retrieve();
        switch (type) {
            case MATERIALS:
                criteriaQuery.where(criteriaBuilder.equal(root.get(BidMaterialModel_.active), true));
                criteriaQuery.where(criteriaBuilder.equal(root.get(BidMaterialModel_.department), department));
                criteriaQuery.where(criteriaBuilder.equal(root.get(BidMaterialModel_.finances), financeDepartment));
                break;
            case EQUIPMENT:
                criteriaQuery.where(criteriaBuilder.equal(root.get(BidEquipmentModel_.active), true));
                criteriaQuery.where(criteriaBuilder.equal(root.get(BidEquipmentModel_.department), department));
                criteriaQuery.where(criteriaBuilder.equal(root.get(BidEquipmentModel_.finances), financeDepartment));
                break;
            case SERVICES:
                criteriaQuery.where(criteriaBuilder.equal(root.get(BidServiceModel_.active), true));
                criteriaQuery.where(criteriaBuilder.equal(root.get(BidServiceModel_.department), department));
                criteriaQuery.where(criteriaBuilder.equal(root.get(BidServiceModel_.finances), financeDepartment));
                break;
        }
        return super.getList();
    }

    public List<BidModel> retrieve(DepartmentModel department, BidType type) throws SQLException {
        super.retrieve();
        switch (type) {
            case MATERIALS:
                criteriaQuery.where(criteriaBuilder.equal(root.get(BidMaterialModel_.active), true));
                criteriaQuery.where(criteriaBuilder.equal(root.get(BidMaterialModel_.department), department));
                break;
            case EQUIPMENT:
                criteriaQuery.where(criteriaBuilder.equal(root.get(BidEquipmentModel_.active), true));
                criteriaQuery.where(criteriaBuilder.equal(root.get(BidEquipmentModel_.department), department));
                break;
            case SERVICES:
                criteriaQuery.where(criteriaBuilder.equal(root.get(BidServiceModel_.active), true));
                criteriaQuery.where(criteriaBuilder.equal(root.get(BidServiceModel_.department), department));
                break;
        }
        return super.getList();
    }

    public List<BidModel> getResults(BidType type) throws SQLException {
        super.retrieve();
        switch (type) {
            case MATERIALS:
                criteriaQuery.where(criteriaBuilder.equal(root.get(BidMaterialModel_.active), true));
                break;
            case EQUIPMENT:
                criteriaQuery.where(criteriaBuilder.equal(root.get(BidEquipmentModel_.active), true));
                break;
            case SERVICES:
                criteriaQuery.where(criteriaBuilder.equal(root.get(BidServiceModel_.active), true));
                break;
        }
        return super.getList();
    }

    @Override
    public List<BidModel> getResults() throws SQLException {
        //not implemented for this class
        return null;
    }

    @Override
    public void createOrUpdate(BidModel object) throws SQLException {
        //not implemented for this class
    }

    public void createOrUpdate(BidModel model, BidType type) throws SQLException {
        EntityManager entityManager = Database.DB.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(type.getTypeClass().cast(model));
        entityManager.getTransaction().commit();
    }

}
