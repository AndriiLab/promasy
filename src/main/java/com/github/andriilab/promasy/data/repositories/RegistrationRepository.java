package com.github.andriilab.promasy.data.repositories;

import com.github.andriilab.promasy.domain.registration.entities.RegistrationTicket;

import javax.persistence.EntityManager;

/**
 * Queries associated with registration of a new user
 */
public class RegistrationRepository {

    private final EntityManager entityManager;

    public RegistrationRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public int getRegistrationsLeft() {
        return Repository.inTransaction(entityManager, () -> {
            RegistrationTicket model = entityManager.find(RegistrationTicket.class, 1);

            return model != null ? model.getRegistrationTicketNumber() : 0;
        });
    }

    public int useRegistration() {
        int registrationNumber = getRegistrationsLeft();

        return Repository.inTransaction(entityManager, () -> {
            RegistrationTicket model = entityManager.find(RegistrationTicket.class, 1);
            int registrationsLeft = registrationNumber;

            if (model != null) {
                model.setRegistrationTicketNumber(registrationsLeft < 0 ? 0 : --registrationsLeft);
            } else {
                model = new RegistrationTicket(registrationsLeft);
            }

            entityManager.persist(model);
            return registrationsLeft;
        });
    }

    public void changeNumberOfRegistrationTickets(int numberOfRegistrationTickets) {
        Repository.inTransaction(entityManager, () -> {
            RegistrationTicket model = entityManager.find(RegistrationTicket.class, 1);
            model.setRegistrationTicketNumber(numberOfRegistrationTickets);
        });
    }
}
