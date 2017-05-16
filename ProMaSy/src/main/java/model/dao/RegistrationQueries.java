package model.dao;

import model.models.RegistrationTicketModel;

import javax.persistence.EntityManager;
import java.sql.SQLException;

/**
 * Queries associated with registration of a new user
 */
public class RegistrationQueries {

    public int getRegistrationsLeft() {
        EntityManager em = Database.DB.getEntityManager();
        em.getTransaction().begin();

        RegistrationTicketModel model = em.find(RegistrationTicketModel.class, 1);

        int registrationsLeft = model != null ? model.getRegistrationTicketNumber() : 0;

        em.getTransaction().commit();

        return registrationsLeft;
    }

    public int useRegistration() throws SQLException {
        int registrationNumber = getRegistrationsLeft();
        EntityManager em = Database.DB.getEntityManager();

        em.getTransaction().begin();
        RegistrationTicketModel model = em.find(RegistrationTicketModel.class, 1);

        if (model != null) {
            model.setRegistrationTicketNumber(registrationNumber < 0 ? 0 : --registrationNumber);
        } else {
            model = new RegistrationTicketModel(registrationNumber);
        }

        em.persist(model);
        em.getTransaction().commit();

        return registrationNumber;
    }

    public void changeNumberOfRegistrationTickets(int numberOfRegistrationTickets) {

        EntityManager em = Database.DB.getEntityManager();
        em.getTransaction().begin();

        RegistrationTicketModel model = em.find(RegistrationTicketModel.class, 1);
        model.setRegistrationTicketNumber(numberOfRegistrationTickets);

        em.getTransaction().commit();
    }
}
