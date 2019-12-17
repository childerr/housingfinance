package com.childer.housingfinance.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Institutions {
    @Id
    @Column(length = 10, nullable = false)
    private String code;

    @Column(length = 50, nullable = false)
    private String name;

    @Builder
    public Institutions(String code, String name){
        this.code = code;
        this.name = name;
    }
}
