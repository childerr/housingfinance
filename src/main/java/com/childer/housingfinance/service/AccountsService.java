package com.childer.housingfinance.service;

import com.childer.housingfinance.dto.AccountsDto;
import com.childer.housingfinance.vo.AccountsVo;

public interface AccountsService {
    void SignUp(AccountsDto accountsDto) throws Exception;
    void SignIn(String id, String password) throws Exception;
    AccountsVo getAccountInfo(String id) throws Exception;
}
