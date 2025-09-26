package kz.javazhan.sigma_finance.domain.DTOS;

import kz.javazhan.sigma_finance.domain.enums.AccountType;
import kz.javazhan.sigma_finance.domain.enums.CurrencyTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateAccountRequestDTO {
    private AccountType accountType;
    private CurrencyTypeEnum currency;
}
