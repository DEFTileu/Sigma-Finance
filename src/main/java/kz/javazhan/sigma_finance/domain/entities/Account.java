package kz.javazhan.sigma_finance.domain.entities;

import jakarta.websocket.server.ServerEndpoint;
import kz.javazhan.sigma_finance.domain.enums.AccountStatusEnum;
import kz.javazhan.sigma_finance.domain.enums.AccountType;
import kz.javazhan.sigma_finance.domain.enums.CurrencyTypeEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public abstract class Account{
    UUID id;
    String accountNumber;
    AccountType accountType;
    long balance;
    CurrencyTypeEnum currencyType;
    AccountStatusEnum status;
}
