package com.childer.housingfinance.repository;

import com.childer.housingfinance.domain.Institutions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InstitutionsRepo extends JpaRepository<Institutions, String> {
    Optional<Institutions> findByName(String s);
}
