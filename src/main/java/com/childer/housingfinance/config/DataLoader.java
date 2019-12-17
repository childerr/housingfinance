package com.childer.housingfinance.config;

import com.childer.housingfinance.domain.Accounts;
import com.childer.housingfinance.domain.Financials;
import com.childer.housingfinance.domain.Institutions;
import com.childer.housingfinance.repository.AccountsRepo;
import com.childer.housingfinance.repository.FinancialsRepo;
import com.childer.housingfinance.repository.InstitutionsRepo;
import com.childer.housingfinance.util.CustomSecurity;
import jdk.internal.module.Resources;
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

    public DataLoader(InstitutionsRepo institutionsRepo, FinancialsRepo financialsRepo) {
        this.institutionsRepo = institutionsRepo;
        this.financialsRepo = financialsRepo;
    }

    public void run(ApplicationArguments args) {
        //userRepository.save(new User("lala", "lala", "lala"));
        Accounts accounts = Accounts.builder().id("childer").name("박영상").password(CustomSecurity.encSHA256("1234")).build();


        List<Institutions> institutions = Arrays.asList(
                Institutions.builder().code("fine2726").name("주택도시기금").build()
            ,   Institutions.builder().code("bank3726").name("국민은행").build()
            ,   Institutions.builder().code("bank4726").name("우리은행").build()
            ,   Institutions.builder().code("bank5726").name("신한은행").build()
            ,   Institutions.builder().code("bank6726").name("한국시티은행").build()
            ,   Institutions.builder().code("bank7726").name("하나은행").build()
            ,   Institutions.builder().code("bank8726").name("농협/수협은행").build()
            ,   Institutions.builder().code("bank9726").name("외환은행").build()
            ,   Institutions.builder().code("bank1726").name("기타은행").build()


        );

        institutionsRepo.saveAll(institutions);

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

        for (List<String> record : records){
            Financials.builder()
                    .year(Short.parseShort(record.get(0))).
        }

//        records.remove(0).forEach(x->{
//            records.get(x).forEach(y->);
//            Financials.builder().year(Integer.getInteger(x[0]))
//        });

        List<Financials> financials = new ArrayList<>();


    }
}
