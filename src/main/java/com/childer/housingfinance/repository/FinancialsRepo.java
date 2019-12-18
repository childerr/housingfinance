package com.childer.housingfinance.repository;

import com.childer.housingfinance.domain.Financials;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FinancialsRepo extends JpaRepository<Financials, Long> {
    List<Financials> findAllByInstitutionsCodeAndYearLessThan(String institutionCode, short Year);
    List<Financials> findAllByInstitutionsCodeOrderByYearAscMonthAsc(String institutionCode);
}
