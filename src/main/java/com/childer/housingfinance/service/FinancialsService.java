package com.childer.housingfinance.service;

import com.childer.housingfinance.dto.PredictionFinancialDto;
import com.childer.housingfinance.vo.FinancialsSupplyVo;
import com.childer.housingfinance.vo.MinMaxSupplyFinantialVo;
import com.childer.housingfinance.vo.PredictionFinancialVo;
import com.childer.housingfinance.vo.TopSupplyInstitutionVo;

import java.util.List;

public interface FinancialsService {
    List<String> getInstitutions() throws Exception;

    List<FinancialsSupplyVo> getFinancialsSuplies() throws Exception;

    TopSupplyInstitutionVo getTopSupplyInstitutionPerYear() throws Exception;

    MinMaxSupplyFinantialVo getMinMaxSupplyFinancialByInstitutionCode(String institutionCode) throws Exception;

    PredictionFinancialVo predictionFinancial(PredictionFinancialDto predictionFinancialDto) throws  Exception;

    PredictionFinancialVo predictionFinancialNew(PredictionFinancialDto predictionFinancialDto) throws  Exception;
}