package com.childer.housingfinance.service;

import com.childer.housingfinance.domain.Financials;
import com.childer.housingfinance.domain.Institutions;
import com.childer.housingfinance.dto.PredictionFinancialDto;
import com.childer.housingfinance.repository.FinancialsRepo;
import com.childer.housingfinance.repository.InstitutionsRepo;
import com.childer.housingfinance.vo.FinancialsSupplyVo;
import com.childer.housingfinance.vo.MinMaxSupplyFinantialVo;
import com.childer.housingfinance.vo.PredictionFinancialVo;
import com.childer.housingfinance.vo.TopSupplyInstitutionVo;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@Service("financialsService")
@AllArgsConstructor
public class FinancialsServiceImpl implements FinancialsService{
    FinancialsRepo financialsRepo;
    InstitutionsRepo institutionsRepo;

    @Override
    public List<String> getInstitutions()
        throws Exception {
        return institutionsRepo.findAll().stream().map(Institutions::getName).collect(toList());
    }

    @Override
    public List<FinancialsSupplyVo> getFinancialsSuplies()
            throws Exception {
        List<Financials> financials = financialsRepo.findAll();//Sort.by(Sort.Direction.ASC, "year").and(Sort.by(Sort.Direction.ASC, "month")));
        List<FinancialsSupplyVo> financialsSupplyVo = new ArrayList<>();

        financials.stream().collect(groupingBy(Financials::getYear, groupingBy(financial->financial.getInstitutions().getName(), summingInt(Financials::getAmount) ))).forEach((k, v)->{
            financialsSupplyVo.add(
                    new FinancialsSupplyVo(
                            String.format("%s", k)
                            ,   v.values().stream().reduce(Integer::sum).get()
                            ,   v
                    )
            );
        });
        financialsSupplyVo.sort(Comparator.comparing(FinancialsSupplyVo::getYear));

        return financialsSupplyVo;
    }

    @Override
    public TopSupplyInstitutionVo getTopSupplyInstitutionPerYear()
            throws Exception {
        List<Financials> financials = financialsRepo.findAll();
        List<TopSupplyInstitutionVo> topSupplyInstitutionVo = new ArrayList<>();
        financials.stream().collect(groupingBy(Financials::getYear, groupingBy(financial->financial.getInstitutions().getName(), summingInt(Financials::getAmount) ))).forEach((k, v)->{
            v.forEach((key, val)->{
                topSupplyInstitutionVo.add(
                    new TopSupplyInstitutionVo(k, key, val)
                );
            });
        });

        return topSupplyInstitutionVo.stream().max(Comparator.comparing(TopSupplyInstitutionVo::getAmount)).get();
    }

    @Override
    public MinMaxSupplyFinantialVo getMinMaxSupplyFinancialByInstitutionCode(String institutionCode)
            throws Exception {

        List<Financials> financials = financialsRepo.findAllByInstitutionsCodeAndYearLessThan(institutionCode, (short)2017);
        String bankName = financials.get(0).getInstitutions().getName();

        List<Map<String, Object>> supportAmounts = new ArrayList<>();

        Map<Short, Double> financialsByBank = financials.stream().collect(groupingBy(Financials::getYear, averagingInt(Financials::getAmount)));

        Map.Entry<Short, Double> MinSet = financialsByBank.entrySet().stream().min(Comparator.comparingDouble(x->x.getValue())).get();
        Map<String, Object> minSupportAmount = new HashMap<>();
        minSupportAmount.put("year", MinSet.getKey());
        minSupportAmount.put("amount", Math.round(MinSet.getValue()));
        supportAmounts.add(minSupportAmount);

        Map.Entry<Short, Double> MaxSet = financialsByBank.entrySet().stream().max(Comparator.comparingDouble(x->x.getValue())).get();
        Map<String, Object> maxSupportAmount = new HashMap<>();
        maxSupportAmount.put("year", MaxSet.getKey());
        maxSupportAmount.put("amount", Math.round(MaxSet.getValue()));
        supportAmounts.add(maxSupportAmount);

        return new MinMaxSupplyFinantialVo(bankName, supportAmounts);
    }

    @Override
    public PredictionFinancialVo predictionFinancial(PredictionFinancialDto predictionFinancialDto)
            throws Exception {
        Institutions institutions = institutionsRepo.findByName(predictionFinancialDto.getBank()).get();
        List<Financials> financials = financialsRepo.findAllByInstitutionsCodeOrderByYearAscMonthAsc(institutions.getCode());
        List<Double> inclinations = new ArrayList<>(); //모든 X축에 대한 기울기
        Double i = 1d;
        for(Financials financial:financials){
            inclinations.add(financial.getAmount()/i++);
        }

        Double minInclination = inclinations.stream().min(Comparator.comparingDouble(Double::doubleValue)).get();
        Double maxInclination = inclinations.stream().max(Comparator.comparingDouble(Double::doubleValue)).get();
        minInclination = Math.round((minInclination * 10)) / 10d;
        maxInclination = Math.round((maxInclination * 10)) / 10d;

        Long minSumOfSquaredError = Long.MAX_VALUE;
        Double predictionInclination = 0d;
        for(double y=minInclination;y <= maxInclination; y=y+0.1 ){
            int x = 1;
            Long sumOfSquaredError = 0l;
            for(Financials financial:financials){
                sumOfSquaredError += (long)Math.pow((financial.getAmount() - (x*y)), 2);
                x++;
            }

            if(minSumOfSquaredError == 0d || sumOfSquaredError < minSumOfSquaredError){
                minSumOfSquaredError = sumOfSquaredError;
                x++;
            }else{
                predictionInclination = y - 0.1;
                break;
            }
        }

        Financials minFinancials = financials.get(0);
        short minYear = minFinancials.getYear();
        byte minMonth = minFinancials.getMonth();
        String bank = minFinancials.getInstitutions().getCode();

        short totalMonth = (short)(((predictionFinancialDto.getYear() - minYear) * 12) + (predictionFinancialDto.getMonth() - minMonth) + 1);
        int predictionAmount = (int)(predictionInclination * totalMonth);

        return new PredictionFinancialVo(bank, predictionFinancialDto.getYear(), predictionFinancialDto.getMonth(), predictionAmount);
    }

    @Override
    public PredictionFinancialVo predictionFinancialNew(PredictionFinancialDto predictionFinancialDto)
            throws Exception {
        Institutions institutions = institutionsRepo.findByName(predictionFinancialDto.getBank()).get();
        List<Financials> financials = financialsRepo.findAllByInstitutionsCodeOrderByYearAscMonthAsc(institutions.getCode());

        //최소제곱법(OLS; Ordinary Least Squares)
        //Y평균 = 기울기 * X평균 + Y절편

        Double averageX = (financials.size() + 1) / 2d;
        Double averageY = financials.stream().collect(summingInt(Financials::getAmount)).doubleValue() / financials.size();

        Double inclinationNumerator = 0d;
        Double inclinationdenominator = 0d;
        int i = 1;
        for(Financials financial : financials){
            Double x = (i - averageX);
            inclinationdenominator += x * (financial.getAmount() - averageY);
            inclinationNumerator += Math.pow(x, 2);
            i++;
        }

        Double predictionInclination = inclinationdenominator / inclinationNumerator;
        Double interceptY = averageY - (predictionInclination * averageX);

        Financials minFinancials = financials.get(0);
        short minYear = minFinancials.getYear();
        byte minMonth = minFinancials.getMonth();
        String bank = minFinancials.getInstitutions().getCode();

        short totalMonth = (short)(((predictionFinancialDto.getYear() - minYear) * 12) + (predictionFinancialDto.getMonth() - minMonth) + 1);
        int predictionAmount = (int)((predictionInclination * totalMonth) + interceptY);

        return new PredictionFinancialVo(bank, predictionFinancialDto.getYear(), predictionFinancialDto.getMonth(), predictionAmount);
    }
}
