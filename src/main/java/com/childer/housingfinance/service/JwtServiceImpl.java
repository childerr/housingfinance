package com.childer.housingfinance.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.lang.String;

@Service("jwtService")
public class JwtServiceImpl implements JwtService {
    @Value("${jwt.security.key}")
    private String tokenKey;

    @Value("${jwt.security.expire}")
    private long tokenExpire;

    private static final SignatureAlgorithm SIGNATUREALGORITHM = SignatureAlgorithm.HS256;
    @Override
    public void generateToken(Map<String, Object> data){
        Claims claims = Jwts.claims().setSubject(data.get("id").toString());
        claims.putAll(data);
        Date now = new Date();
        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenExpire))
                .signWith(this.generateKey())
                .compact();

        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        response.addHeader("Authorization", token);
    }

    @Override
    public Map<String, Object> getToken() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String token = request.getHeader("Authorization");
        String tokenWithoutBearer = token.replaceFirst("(?i)bearer\\s?token\\s?(.+)", "$1");

        Claims claims = null;

        if(!token.isEmpty()) {
            claims = Jwts.parser()
                    .setSigningKey(this.generateKey())
                    .parseClaimsJws(token).getBody();
            if(!claims.getExpiration().before(new Date()) && !token.equals(tokenWithoutBearer)){
                generateToken(claims);
            }
        }
        return claims;
    }

    @Override
    public boolean validateToken() {
        boolean result = false;
        try {
            Claims claims = (Claims)getToken();
            if(claims != null) {
                result = claims.getExpiration().before(new Date());
            }
        } catch (Exception e) {

        }
        return result;
    }

    private Key generateKey(){
        Key signingKey = null;

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] keyBytes = DatatypeConverter.parseBase64Binary(tokenKey);
        signingKey = new SecretKeySpec(keyBytes, SIGNATUREALGORITHM.getJcaName());

        return signingKey;
    }
}
