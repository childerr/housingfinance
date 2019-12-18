package com.childer.housingfinance.controller;

import com.childer.housingfinance.dto.PredictionFinancialDto;
import com.childer.housingfinance.service.FinancialsService;
import com.childer.housingfinance.vo.MinMaxSupplyFinantialVo;
import com.childer.housingfinance.vo.PredictionFinancialVo;
import com.childer.housingfinance.vo.TopSupplyInstitutionVo;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
public class FinancialsController {
    FinancialsService financialsService;

    @GetMapping("/financial/institutions")
    @ResponseBody
    public List<String> getInstitutions()
            throws Exception{

        return financialsService.getInstitutions();
    }

    @GetMapping("/financial/status")
    @ResponseBody
    public Map<String, Object> getFinancialSupply()
            throws Exception{

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("name", "주택금융 공급현황");
        result.put("items", financialsService.getFinancialsSuplies());

        return result;
    }

    @GetMapping("/financial/topinstitution")
    @ResponseBody
    public TopSupplyInstitutionVo getTopSupplyInstitution()
            throws Exception{

        return financialsService.getTopSupplyInstitutionPerYear();
    }

    @GetMapping("/financial/minandmaxfinancial")
    @ResponseBody
    public MinMaxSupplyFinantialVo getMinMaxSupplyFinancial()
            throws Exception{

        return financialsService.getMinMaxSupplyFinancialByInstitutionCode("bank3786");
    }

    @GetMapping("/financial/predictionfinancial")
    @ResponseBody
    public PredictionFinancialVo getPredictionFinancial(@RequestBody PredictionFinancialDto predictionFinancialDto)
            throws Exception{
        predictionFinancialDto.setYear((short)2018);
        return financialsService.predictionFinancial(predictionFinancialDto);
    }

    @GetMapping("/financial/predictionfinancialnew")
    @ResponseBody
    public PredictionFinancialVo getPredictionFinancialNew(@RequestBody PredictionFinancialDto predictionFinancialDto)
            throws Exception{
        predictionFinancialDto.setYear((short)2018);
        return financialsService.predictionFinancialNew(predictionFinancialDto);
    }
}
