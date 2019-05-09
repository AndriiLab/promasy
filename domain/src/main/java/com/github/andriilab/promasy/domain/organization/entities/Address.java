package com.github.andriilab.promasy.domain.organization.entities;

import com.github.andriilab.promasy.domain.AbstractEntity;
import com.github.andriilab.promasy.domain.organization.enums.CityTypes;
import com.github.andriilab.promasy.domain.organization.enums.StreetTypes;
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
    @Getter @Setter private String country;

    @Column
    @Getter @Setter private String region;

    @Enumerated(EnumType.STRING)
    @Getter @Setter private CityTypes cityType;

    @Column
    @Getter @Setter private String city;

    @Enumerated(EnumType.STRING)
    @Getter @Setter private StreetTypes streetType;

    @Column
    @Getter @Setter private String street;

    @Column(name = "building_number")
    @Getter @Setter private String buildingNumber;

    @Column(name = "corpus_number")
    @Getter @Setter private String corpusNumber;

    @Column(name = "postal_code")
    @Getter @Setter private String postalCode;

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
        return streetType + " " + street + ", " + buildingNumber + ", " +
                (corpusNumber == null ? "" : corpusNumber + ", ") +
                city + ", " +
                region + ", " + country + ", " +
                postalCode;
//        return streetType.getShortName() + " " + street + ", " + buildingNumber + ", " +
//                (corpusNumber == null ? "" : corpusNumber + ", ") +
//                city + ", " +
//                region + Labels.withSpaceBefore("region") + ", " + country + ", " +
//                postalCode; todo
    }
}
