package com.childer.housingfinance.config;

import com.childer.housingfinance.domain.Accounts;
import com.childer.housingfinance.domain.Institutions;
import com.childer.housingfinance.repository.AccountsRepo;
import com.childer.housingfinance.repository.FinancialsRepo;
import com.childer.housingfinance.repository.InstitutionsRepo;
import com.childer.housingfinance.util.CustomSecurity;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

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



        institutionsRepo.saveAll()
    }
}
