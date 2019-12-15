package com.childer.housingfinance.controller;

import com.childer.housingfinance.dto.BaseAccountDto;
import com.childer.housingfinance.dto.AccountsDto;
import com.childer.housingfinance.service.AccountsService;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class AccountsController {
    private AccountsService accountsService;

    @GetMapping("/test")
    public String test() {
        return "helloworld";
    }

    @PostMapping("/signup")
    public void saveAccounts(@RequestBody AccountsDto accountDto)
            throws Exception{
        accountsService.SignUp(accountDto);
        //accountsRepo.save(accountDto.toEntity());
    }

    @ResponseBody
    @GetMapping("/signin")
    public BaseAccountDto findAccounts(String id)
            throws Exception{
/*        String password = accountDto.getPassword();
        accountDto.setPassword(encSHA256(password));
        accountsRepo.save(accountDto.toEntity());

        String id = accountDto.getId();*/
        return accountsService.SignIn(id);
    }


}
