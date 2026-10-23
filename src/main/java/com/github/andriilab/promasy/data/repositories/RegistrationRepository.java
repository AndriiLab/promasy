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
        return Repository.readOnly(entityManager, () -> {
            RegistrationTicket model = entityManager.find(RegistrationTicket.class, 1);

            return model != null ? model.getRegistrationTicketNumber() : 0;
        });
    }

    /**
     * Takes one registration ticket, if there is one, in a single transaction.
     *
     * @return the number of tickets there were <em>before</em> this call, so a positive result means a ticket was
     * taken and the registration is allowed; {@code 0} means there was none and nothing changed
     */
    public int useRegistration() {
        return Repository.inTransaction(entityManager, () -> {
            RegistrationTicket model = entityManager.find(RegistrationTicket.class, 1);
            if (model == null) {
                entityManager.persist(new RegistrationTicket(0));
                return 0;
            }

            int ticketsBefore = model.getRegistrationTicketNumber();
            if (ticketsBefore > 0) {
                model.setRegistrationTicketNumber(ticketsBefore - 1);
            }
            return ticketsBefore;
        });
    }

    public void changeNumberOfRegistrationTickets(int numberOfRegistrationTickets) {
        Repository.inTransaction(entityManager, () -> {
            RegistrationTicket model = entityManager.find(RegistrationTicket.class, 1);
            if (model != null) {
                model.setRegistrationTicketNumber(numberOfRegistrationTickets);
            } else {
                entityManager.persist(new RegistrationTicket(numberOfRegistrationTickets));
            }
        });
    }
}
