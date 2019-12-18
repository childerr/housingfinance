package com.childer.housingfinance.controller;

import com.childer.housingfinance.dto.AccountsDto;
import com.childer.housingfinance.service.AccountsService;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class AccountsController {
    private AccountsService accountsService;

    @PostMapping("/account/signup")
    public void saveAccounts(@RequestBody AccountsDto accountDto)
            throws Exception{
        accountsService.SignUp(accountDto);
    }

    @PostMapping("/account/signin")
    public void accessAccounts(@RequestBody AccountsDto accountDto)
            throws Exception{
        accountsService.SignIn(accountDto.getId(), accountDto.getPassword());
    }
}
