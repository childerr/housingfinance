package com.childer.housingfinance.security;

import com.childer.housingfinance.filter.JwtAuthenticationFilter;
import com.childer.housingfinance.service.AccountsService;
import com.childer.housingfinance.service.JwtService;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    private JwtService jwtService;
    private AccountsService accountsService;
    public JwtConfigurer(JwtService jwtService, AccountsService accountsService) {
        this.jwtService = jwtService;
        this.accountsService = accountsService;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        JwtAuthenticationFilter customFilter = new JwtAuthenticationFilter(jwtService,accountsService);
        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    }
}