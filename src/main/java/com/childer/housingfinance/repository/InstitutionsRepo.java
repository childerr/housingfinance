package com.childer.housingfinance.repository;

import com.childer.housingfinance.domain.Institutions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstitutionsRepo extends JpaRepository<Institutions, String> {

}
