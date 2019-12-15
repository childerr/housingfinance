package com.childer.housingfinance.dto;

import com.childer.housingfinance.domain.Accounts;
import com.childer.housingfinance.util.CustomSecurity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AccountsDto extends BaseAccountDto {
    private String password;

    public String GetPasswordByEnc(){
        return new CustomSecurity().encSHA256(this.password);
    }

    public Accounts toEntity() {
        return Accounts.builder()
                .id(super.getId())
                .name(super.getName())
                //.password(password)
                .password(new CustomSecurity().encSHA256(password))
                .build();
    }
/*
    public Accounts toEntityWithPasswordEnc() {
        return Accounts.builder()
                .id(id)
                .password(new CustomSecurity().encSHA256(password))
                .build();
    }
*/
}
