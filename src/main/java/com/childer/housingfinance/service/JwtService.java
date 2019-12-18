package com.childer.housingfinance.service;

import io.jsonwebtoken.Claims;

public interface JwtService {
    //void generateToken(Map<String, Object> data);
    //Map<String, Object> getToken();
    void generateToken(String id);
    Claims getToken();
    boolean validateToken();
}
