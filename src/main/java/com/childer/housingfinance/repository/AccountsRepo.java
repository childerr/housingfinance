package com.childer.housingfinance.repository;

import com.childer.housingfinance.domain.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountsRepo extends JpaRepository<Accounts, String> {
}
