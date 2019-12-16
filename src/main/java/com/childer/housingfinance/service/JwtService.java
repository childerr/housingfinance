package com.childer.housingfinance.service;

import java.util.Map;

public interface JwtService {
    void GenerateToken(Map<String, Object> data);
    Map<String, Object> getToken();
}
