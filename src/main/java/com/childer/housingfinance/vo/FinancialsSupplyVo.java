package com.childer.housingfinance.vo;

import lombok.Value;

import java.util.Map;

@Value
public class FinancialsSupplyVo {
    String year;
    int total_amount;
    Map<String, Integer> detail_amount;
}
