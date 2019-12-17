package com.childer.housingfinance.service;

import java.util.Map;

public interface JwtService {
    void generateToken(Map<String, Object> data);
    Map<String, Object> getToken();
    boolean validateToken();
}
