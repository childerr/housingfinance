package com.childer.housingfinance.dto;

import com.childer.housingfinance.domain.Accounts;
import com.childer.housingfinance.util.CustomSecurity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AccountsDto {
    private String id;
    private String password;
    private String name;

    public Accounts toEntity() {
        return Accounts.builder()
                .id(this.id)
                .name(this.name)
                //.password(password)
                .password(CustomSecurity.encSHA256(this.password))
                .build();
    }
}
