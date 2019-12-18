package com.childer.housingfinance.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Value;

@Value
public class TopSupplyInstitutionVo {
    short year;
    String bank;
    @JsonIgnore
    int amount;
}
