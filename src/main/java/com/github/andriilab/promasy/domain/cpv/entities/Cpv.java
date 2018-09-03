package com.github.andriilab.promasy.domain.cpv.entities;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cpv")
public class Cpv {

    @Id
    @Column(name = "cpv_code")
    private @Getter
    String cpvId;

    @Column(name = "cpv_ukr")
    private @Getter
    String cpvUkr;

    @Column(name = "cpv_eng")
    private @Getter
    String cpvEng;

    @Column(name = "cpv_level")
    private @Getter
    int cpvLevel;

    @Column(name = "terminal")
    private @Getter
    boolean cpvTerminal;

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
