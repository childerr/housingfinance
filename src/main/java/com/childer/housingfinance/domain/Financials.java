package com.childer.housingfinance.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Financials {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private short year;

    @Column(nullable = false)
    private byte month;

    @Column(nullable = false)
    private int amount;

    @ManyToOne
    @JoinColumn
    private Institutions institutions;

    @Builder
    public Financials(short year, byte month, int amount, Institutions institutions){
        this.year = year;
        this.month = month;
        this.amount = amount;
        this.institutions = institutions;
    }
}