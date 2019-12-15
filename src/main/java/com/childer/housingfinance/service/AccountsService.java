package com.childer.housingfinance.service;

import com.childer.housingfinance.dto.AccountsDto;
import com.childer.housingfinance.dto.BaseAccountDto;

public interface AccountsService {
    void SignUp(AccountsDto accountsDto) throws Exception;
    BaseAccountDto SignIn(String id) throws Exception;
}
