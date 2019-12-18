package com.childer.housingfinance.config;

import com.childer.housingfinance.domain.Accounts;
import com.childer.housingfinance.domain.Financials;
import com.childer.housingfinance.domain.Institutions;
import com.childer.housingfinance.repository.AccountsRepo;
import com.childer.housingfinance.repository.FinancialsRepo;
import com.childer.housingfinance.repository.InstitutionsRepo;
import com.childer.housingfinance.util.CustomSecurity;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class DataLoader implements ApplicationRunner {
    private InstitutionsRepo institutionsRepo;
    private FinancialsRepo financialsRepo;
    private AccountsRepo accountsRepo;

    public DataLoader(InstitutionsRepo institutionsRepo, FinancialsRepo financialsRepo,AccountsRepo accountsRepo) {
        this.institutionsRepo = institutionsRepo;
        this.financialsRepo = financialsRepo;
        this.accountsRepo = accountsRepo;
    }

    public void run(ApplicationArguments args) {
        Accounts accounts = Accounts.builder().id("childer").name("박영상").password(CustomSecurity.encSHA256("1234")).build();

        List<Institutions> institutions = Arrays.asList(
                Institutions.builder().code("bank3716").name("주택도시기금").build()
            ,   Institutions.builder().code("bank3726").name("국민은행").build()
            ,   Institutions.builder().code("bank3736").name("우리은행").build()
            ,   Institutions.builder().code("bank3746").name("신한은행").build()
            ,   Institutions.builder().code("bank3756").name("한국시티은행").build()
            ,   Institutions.builder().code("bank3766").name("하나은행").build()
            ,   Institutions.builder().code("bank3776").name("농협/수협은행").build()
            ,   Institutions.builder().code("bank3786").name("외환은행").build()
            ,   Institutions.builder().code("bank3796").name("기타은행").build()


        );

        List<List<String>> records = new ArrayList<>();

        String fileName = "financing.csv";
        ClassLoader classLoader = this.getClass().getClassLoader();
        String file = classLoader.getResource(fileName).getFile();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                line = line.replaceAll("\"(\\d+),(\\d+)\"", "$1$2");
                String[] values = line.split(",");
                records.add(Arrays.asList(values));
            }
        } catch (FileNotFoundException e){

        } catch (IOException e){

        }

        List<Financials> financials = new ArrayList<>();
        for (List<String> record : records.subList(1,records.size())){
            int i = 0;
            for(Institutions institution : institutions){
                financials.add(
                        Financials.builder()
                        .year(Short.parseShort(record.get(0)))
                        .month(Byte.parseByte(record.get(1)))
                        .amount(Integer.parseInt(record.get(2+(i++))))
                        .institutions(institution)
                        .build()
                );
            }
        }

        accountsRepo.save(accounts);
        institutionsRepo.saveAll(institutions);
        financialsRepo.saveAll(financials);
    }
}
