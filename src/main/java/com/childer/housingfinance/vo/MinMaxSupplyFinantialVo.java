package com.childer.housingfinance.vo;

import lombok.Value;

import java.util.List;
import java.util.Map;

@Value
public class MinMaxSupplyFinantialVo {
    String Bank;
    List<Map<String, Object>> support_amount;
}
