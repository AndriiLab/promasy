package model.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * POJO for number of registrations left
 */
@Entity
@Table(name = "registrations")
public class RegistrationTicketModel {

    @Id
    @Column(name = "id")
    private int id = 1;

    @Column(name = "registrations_left")
    private int registrationTicketNumber;

    public RegistrationTicketModel() {
    }

    public RegistrationTicketModel(int registrationTicketNumber) {
        this.registrationTicketNumber = registrationTicketNumber;
    }

    public int getRegistrationTicketNumber() {
        return registrationTicketNumber;
    }

    public void setRegistrationTicketNumber(int registrationTicketNumber) {
        this.registrationTicketNumber = registrationTicketNumber;
    }
}
