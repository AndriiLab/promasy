package com.github.andriilab.promasy.domain.organization.entities;

import com.github.andriilab.promasy.domain.AbstractEntity;
import com.github.andriilab.promasy.domain.organization.enums.CityTypes;
import com.github.andriilab.promasy.domain.organization.enums.StreetTypes;
import com.github.andriilab.promasy.presentation.commons.Labels;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Basic address class
 */

@Entity
@Table(name = "addresses")
public class Address extends AbstractEntity {

    @Column
    private String country;

    @Column
    private String region;

    @Enumerated(EnumType.STRING)
    private CityTypes cityType;

    @Column
    private String city;

    @Enumerated(EnumType.STRING)
    private StreetTypes streetType;

    @Column
    private String street;

    @Column(name = "building_number")
    private String buildingNumber;

    @Column(name = "corpus_number")
    private String corpusNumber;

    @Column(name = "postal_code")
    private String postalCode;

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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public CityTypes getCityType() {
        return cityType;
    }

    public void setCityType(CityTypes cityType) {
        this.cityType = cityType;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public StreetTypes getStreetType() {
        return streetType;
    }

    public void setStreetType(StreetTypes streetType) {
        this.streetType = streetType;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getBuildingNumber() {
        return buildingNumber;
    }

    public void setBuildingNumber(String buildingNumber) {
        this.buildingNumber = buildingNumber;
    }

    public String getCorpusNumber() {
        return corpusNumber;
    }

    public void setCorpusNumber(String corpusNumber) {
        this.corpusNumber = corpusNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Override
    public void setDescription(String description) {
        //won't need here
    }

    @Override
    public String getMessage() {
        return null;
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
