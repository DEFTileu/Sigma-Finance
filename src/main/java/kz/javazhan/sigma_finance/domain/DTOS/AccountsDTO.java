package kz.javazhan.sigma_finance.domain.DTOS;


import kz.javazhan.sigma_finance.domain.enums.AccountStatusEnum;
import kz.javazhan.sigma_finance.domain.enums.AccountType;
import kz.javazhan.sigma_finance.domain.enums.CurrencyTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountsDTO {
    private UUID id;
    private String accountNumber;
    private AccountType accountType;
    private CurrencyTypeEnum currency;
    private AccountStatusEnum status;
    private BigDecimal balance;
    private Long ownerId;
}
