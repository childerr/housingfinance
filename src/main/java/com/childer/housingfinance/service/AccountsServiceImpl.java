package com.childer.housingfinance.service;

import com.childer.housingfinance.domain.Accounts;
import com.childer.housingfinance.dto.AccountsDto;
import com.childer.housingfinance.util.CustomSecurity;
import com.childer.housingfinance.vo.AccountsVo;
import com.childer.housingfinance.repository.AccountsRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

@Service("accountsService")
@AllArgsConstructor
public class AccountsServiceImpl implements AccountsService {
    private AccountsRepo accountsRepo;
    private JwtService jwtService;

    @Override
    public void SignUp(AccountsDto accountsDto)
            throws Exception{

        if (accountsRepo.existsById(accountsDto.getId())) {
            throw new EntityExistsException();
        }

        accountsRepo.save(accountsDto.toEntity());

//        Map<String, Object> data = new HashMap<String, Object>();
//        data.put("id",accountsDto.getId());
//        data.put("name",accountsDto.getName());
        jwtService.generateToken(accountsDto.getId());
    }

    @Override
    public void SignIn(String id, String password)
            throws Exception{
        Assert.hasText(id, "parameter id is empty.");

        Assert.hasText(password, "parameter password not found");

        Accounts accounts = accountsRepo.findById(id).orElseThrow(EntityNotFoundException::new);

        boolean isMatched = accounts.getPassword().equals(CustomSecurity.encSHA256(password));
        Assert.isTrue(isMatched, "password not matched.");

//        Map<String, Object> data = new HashMap<String, Object>();
//        data.put("id", accounts.getId());
//        data.put("name", accounts.getName());
        jwtService.generateToken(accounts.getId());
    }

    @Override
    public AccountsVo getAccountInfo(String id)
            throws Exception {

        AccountsVo accountsVo = accountsRepo.findAccountsById(id).orElse(null);
        return accountsVo;
    }
}
