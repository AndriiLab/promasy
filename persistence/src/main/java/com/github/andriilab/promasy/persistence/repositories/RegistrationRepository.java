package com.github.andriilab.promasy.persistence.repositories;

import com.github.andriilab.promasy.domain.registration.entities.RegistrationTicket;

import javax.persistence.EntityManager;

/**
 * Queries associated with registration of a new user
 */
public class RegistrationRepository {

    private EntityManager entityManager;

    public RegistrationRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public int getRegistrationsLeft() {
        entityManager.getTransaction().begin();

        RegistrationTicket model = entityManager.find(RegistrationTicket.class, 1);

        int registrationsLeft = model != null ? model.getRegistrationTicketNumber() : 0;

        entityManager.getTransaction().commit();

        return registrationsLeft;
    }

    public int useRegistration() {
        int registrationNumber = getRegistrationsLeft();

        entityManager.getTransaction().begin();
        RegistrationTicket model = entityManager.find(RegistrationTicket.class, 1);

        if (model != null) {
            model.setRegistrationTicketNumber(registrationNumber < 0 ? 0 : --registrationNumber);
        } else {
            model = new RegistrationTicket(registrationNumber);
        }

        entityManager.persist(model);
        entityManager.getTransaction().commit();

        return registrationNumber;
    }

    public void changeNumberOfRegistrationTickets(int numberOfRegistrationTickets) {

        entityManager.getTransaction().begin();

        RegistrationTicket model = entityManager.find(RegistrationTicket.class, 1);
        model.setRegistrationTicketNumber(numberOfRegistrationTickets);

        entityManager.getTransaction().commit();
    }
}
