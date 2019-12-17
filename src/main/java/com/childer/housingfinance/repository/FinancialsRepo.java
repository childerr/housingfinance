package com.childer.housingfinance.repository;

import com.childer.housingfinance.domain.Financials;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinancialsRepo extends JpaRepository<Financials, Long> {

}
