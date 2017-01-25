package model.dao;

import model.models.RegistrationTicketModel;

import javax.persistence.EntityManager;
import java.sql.SQLException;

/**
 * Queries associated with registration of a new user
 */
public class RegistrationQueries {

    public int getRegistrationNumber() throws SQLException {
        int registrationNumber;
        EntityManager em = Database.DB.getEntityManager();

        em.getTransaction().begin();
        RegistrationTicketModel model = em.find(RegistrationTicketModel.class, 1);

        if (model != null) {
            registrationNumber = model.getRegistrationTicketNumber();

            model.setRegistrationTicketNumber(--registrationNumber);

        } else {
            // TODO change to function
            registrationNumber = 100;

            model = new RegistrationTicketModel(registrationNumber);
        }

        em.persist(model);
        em.getTransaction().commit();

        return registrationNumber;
    }

    public void changeNumberOfRegistrationTickets(int numberOfRegistrationTickets) {
        if (numberOfRegistrationTickets < 0) {
            numberOfRegistrationTickets = 0;
        }

        EntityManager em = Database.DB.getEntityManager();
        em.getTransaction().begin();

        RegistrationTicketModel model = em.find(RegistrationTicketModel.class, 1);
        model.setRegistrationTicketNumber(numberOfRegistrationTickets);

        em.getTransaction().commit();
    }
}
