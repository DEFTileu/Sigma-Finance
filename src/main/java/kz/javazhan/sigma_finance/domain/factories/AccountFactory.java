package kz.javazhan.sigma_finance.domain.factories;

import kz.javazhan.sigma_finance.domain.entities.BankAccount;
import kz.javazhan.sigma_finance.domain.entities.User;
import kz.javazhan.sigma_finance.domain.enums.AccountType;
import kz.javazhan.sigma_finance.domain.enums.CurrencyTypeEnum;
import kz.javazhan.sigma_finance.domain.enums.AccountStatusEnum;

public interface AccountFactory {
    BankAccount createAccount(String accountNumber, long initialBalance, CurrencyTypeEnum currency, AccountStatusEnum status, User owner);
    AccountType getAccountType();
}
