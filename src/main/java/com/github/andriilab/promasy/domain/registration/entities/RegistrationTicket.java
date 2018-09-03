package com.github.andriilab.promasy.domain.registration.entities;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * POJO for number of registrations left
 */
@Entity
@Table(name = "registrations")
public class RegistrationTicket {

    @Id
    @Column(name = "id")
    private int id = 1;

    @Column(name = "registrations_left")
    private @Getter
    int registrationTicketNumber;

    public RegistrationTicket() {
    }

    public RegistrationTicket(int registrationTicketNumber) {
        this.registrationTicketNumber = registrationTicketNumber < 0 ? 0 : registrationTicketNumber;
    }

    public void setRegistrationTicketNumber(int registrationTicketNumber) {
        this.registrationTicketNumber = registrationTicketNumber < 0 ? 0 : registrationTicketNumber;
    }
}
