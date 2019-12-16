package com.childer.housingfinance.repository;

import com.childer.housingfinance.domain.Accounts;
import com.childer.housingfinance.vo.AccountsVo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountsRepo extends JpaRepository<Accounts, String> {
    Optional<AccountsVo> findAccountsById(String id);
}
