package com.childer.housingfinance.vo;

import lombok.Value;

@Value
public class PredictionFinancialVo {
    String bank;
    short year;
    byte month;
    int amount;
}
