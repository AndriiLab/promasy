package com.github.andriilab.promasy.domain.organization.entities;

import com.github.andriilab.promasy.domain.AbstractEntity;
import com.github.andriilab.promasy.domain.organization.enums.CityTypes;
import com.github.andriilab.promasy.domain.organization.enums.StreetTypes;
import com.github.andriilab.promasy.presentation.commons.Labels;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Basic address class
 */

@Entity
@Table(name = "addresses")
public class Address extends AbstractEntity {

    @Column
    private @Getter
    @Setter
    String country;

    @Column
    private @Getter
    @Setter
    String region;

    @Enumerated(EnumType.STRING)
    private @Getter
    @Setter
    CityTypes cityType;

    @Column
    private @Getter
    @Setter
    String city;

    @Enumerated(EnumType.STRING)
    private @Getter
    @Setter
    StreetTypes streetType;

    @Column
    private @Getter
    @Setter
    String street;

    @Column(name = "building_number")
    private @Getter
    @Setter
    String buildingNumber;

    @Column(name = "corpus_number")
    private @Getter
    @Setter
    String corpusNumber;

    @Column(name = "postal_code")
    private @Getter
    @Setter
    String postalCode;

    public Address(String country, String region, CityTypes cityType, String city, StreetTypes streetType, String street, String buildingNumber, String corpusNumber, String postalCode) {
        this.country = country;
        this.region = region;
        this.cityType = cityType;
        this.city = city;
        this.streetType = streetType;
        this.street = street;
        this.buildingNumber = buildingNumber;
        this.corpusNumber = corpusNumber;
        this.postalCode = postalCode;
    }

    public Address(long modelId, Employee createdEmployee, Timestamp createdDate, Employee modifiedEmployee, Timestamp modifiedDate, boolean active, String country, String region, CityTypes cityType, String city, StreetTypes streetType, String street, String buildingNumber, String corpusNumber, String postalCode) {
        super(modelId, createdEmployee, createdDate, modifiedEmployee, modifiedDate, active);
        this.country = country;
        this.region = region;
        this.cityType = cityType;
        this.city = city;
        this.streetType = streetType;
        this.street = street;
        this.buildingNumber = buildingNumber;
        this.corpusNumber = corpusNumber;
        this.postalCode = postalCode;
    }

    public Address() {
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public void setDescription(String description) { // not used
    }

    @Override
    public String getMessage() {
        return "";
    }

    @Override
    public String toString() {
        return streetType.getShortName() + " " + street + ", " + buildingNumber + ", " +
                (corpusNumber == null ? "" : corpusNumber + ", ") +
                city + ", " +
                region + Labels.withSpaceBefore("region") + ", " + country + ", " +
                postalCode;
    }
}
