package com.github.andriilab.promasy.domain.registration.entities;

import lombok.Getter;

import jakarta.persistence.*;

/**
 * POJO for number of registrations left
 */
@Entity
@Table(name = "registrations")
public class RegistrationTicket {

    @Id
    @Column(name = "id")
    private final int id = 1;

    @Column(name = "registrations_left")
    @Getter private int registrationTicketNumber;

    public RegistrationTicket() {
    }

    public RegistrationTicket(int registrationTicketNumber) {
        this.registrationTicketNumber = registrationTicketNumber < 0 ? 0 : registrationTicketNumber;
    }

    public void setRegistrationTicketNumber(int registrationTicketNumber) {
        this.registrationTicketNumber = registrationTicketNumber < 0 ? 0 : registrationTicketNumber;
    }
}
