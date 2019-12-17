package com.childer.housingfinance.filter;

import com.childer.housingfinance.service.AccountsService;
import com.childer.housingfinance.service.JwtService;
import com.childer.housingfinance.vo.AccountsVo;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends GenericFilterBean {
    private JwtService jwtService;
    private AccountsService accountsService;

    public JwtAuthenticationFilter(JwtService jwtService, AccountsService accountsService) {
        this.jwtService         = jwtService;
        this.accountsService    = accountsService;
    }

    // Request로 들어오는 Jwt Token의 유효성을 검증(jwtTokenProvider.validateToken)하는 filter를 filterChain에 등록합니다.
    @SneakyThrows
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        if (jwtService.validateToken()) {
            String id = jwtService.getToken().get("id").toString();
            AccountsVo accountsVo = accountsService.getAccountInfo(id);
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(accountsVo, accountsVo.getId()));
        }
        filterChain.doFilter(request, response);
    }
}
