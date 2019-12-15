package com.childer.housingfinance.service;

import com.childer.housingfinance.domain.Accounts;
import com.childer.housingfinance.dto.AccountsDto;
import com.childer.housingfinance.dto.BaseAccountDto;
import com.childer.housingfinance.repository.AccountsRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service("accountsService")
@AllArgsConstructor
public class AccountsServiceImpl implements AccountsService {
    private AccountsRepo accountsRepo;

    @Override
    public void SignUp(AccountsDto accountsDto)
            throws Exception{
        /*
            Accounts accounts = Accounts.builder()
                .id(accountsDto.getId())
                .name(accountsDto.getName())
                .password(accountsDto.GetPasswordByEnc())
                .build();
         */
        accountsRepo.save(accountsDto.toEntity());
    }

    @Override
    public BaseAccountDto SignIn(String id)
            throws Exception{
        Accounts account = accountsRepo.findById(id).get();
        BaseAccountDto baseAccountsDto = new BaseAccountDto(account.getId(), account.getName());

        //accountsDto.setPassword(account.getPassword());
        return baseAccountsDto;
    }
}
