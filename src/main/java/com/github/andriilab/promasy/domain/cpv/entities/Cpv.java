package com.github.andriilab.promasy.domain.cpv.entities;

import lombok.Getter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "cpv")
public class Cpv {

    @Id
    @Column(name = "cpv_code")
    @Getter private String cpvId;

    @Column(name = "cpv_ukr")
    @Getter private String cpvUkr;

    @Column(name = "cpv_eng")
    @Getter private String cpvEng;

    @Column(name = "cpv_level")
    @Getter private int cpvLevel;

    @Column(name = "terminal")
    @Getter private boolean cpvTerminal;

    public Cpv(String cpvId, String cpvUkr, String cpvEng,
               int cpvLevel, boolean cpvTerminal) {
        this.cpvId = cpvId;
        this.cpvUkr = cpvUkr;
        this.cpvEng = cpvEng;
        this.cpvLevel = cpvLevel;
        this.cpvTerminal = cpvTerminal;
    }

    public Cpv() {
    }

    @Override
    public String toString() {
        return cpvUkr;
    }
}
