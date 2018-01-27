package com.github.andriilab.promasy.data.queries;

import com.github.andriilab.promasy.data.storage.Database;
import com.github.andriilab.promasy.domain.registration.entities.RegistrationTicket;

import javax.persistence.EntityManager;

/**
 * Queries associated with registration of a new user
 */
public class RegistrationQueries {

    public int getRegistrationsLeft() {
        EntityManager em = Database.CONNECTOR.getEntityManager();
        em.getTransaction().begin();

        RegistrationTicket model = em.find(RegistrationTicket.class, 1);

        int registrationsLeft = model != null ? model.getRegistrationTicketNumber() : 0;

        em.getTransaction().commit();

        return registrationsLeft;
    }

    public int useRegistration() {
        int registrationNumber = getRegistrationsLeft();
        EntityManager em = Database.CONNECTOR.getEntityManager();

        em.getTransaction().begin();
        RegistrationTicket model = em.find(RegistrationTicket.class, 1);

        if (model != null) {
            model.setRegistrationTicketNumber(registrationNumber < 0 ? 0 : --registrationNumber);
        } else {
            model = new RegistrationTicket(registrationNumber);
        }

        em.persist(model);
        em.getTransaction().commit();

        return registrationNumber;
    }

    public void changeNumberOfRegistrationTickets(int numberOfRegistrationTickets) {

        EntityManager em = Database.CONNECTOR.getEntityManager();
        em.getTransaction().begin();

        RegistrationTicket model = em.find(RegistrationTicket.class, 1);
        model.setRegistrationTicketNumber(numberOfRegistrationTickets);

        em.getTransaction().commit();
    }
}
