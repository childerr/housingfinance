package com.childer.housingfinance.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PredictionFinancialDto {
    private String bank;
    private short year;
    private byte month;
}
