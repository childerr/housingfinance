package com.childer.housingfinance.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Accounts {
    @Id
    @Column(length = 200, nullable = false)
    private String id;

    @Column(length = 66, nullable = false)
    private String password;

    @Column(length = 50, nullable = false)
    private String name;

    @Builder
    public Accounts(String id, String password, String name){
        this.id         = id;
        this.password   = password;
        this.name       = name;
    }
}
