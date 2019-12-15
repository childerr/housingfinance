package com.childer.housingfinance.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseAccountDto {
    public BaseAccountDto() {
    }

    public BaseAccountDto(String id, String name){
        this.id     = id;
        this.name   = name;
    }

    private String id;
    private String name;
}
