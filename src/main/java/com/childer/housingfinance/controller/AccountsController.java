package com.childer.housingfinance.controller;

import com.childer.housingfinance.service.JwtService;
import com.childer.housingfinance.vo.AccountsVo;
import com.childer.housingfinance.dto.AccountsDto;
import com.childer.housingfinance.service.AccountsService;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@AllArgsConstructor
public class AccountsController {
    private AccountsService accountsService;
    private JwtService jwtService;

    @GetMapping("/test")
    public String test() {
        return "helloworld";
    }

    @PostMapping("/account/signup")
    public void saveAccounts(@RequestBody AccountsDto accountDto, HttpServletResponse response)
            throws Exception{
        accountsService.SignUp(accountDto);
    }

    @ResponseBody
    @GetMapping("/account/signin")
    public void accessAccounts(String id, String password)
            throws Exception{

        accountsService.SignIn(id, password);
    }

//    @ResponseBody
//    @GetMapping("/account/validatetoken")
//    public AccountsVo findAccounts()
//            throws Exception{
//        //return accountsService.getAccountInfo();
//    }
}
