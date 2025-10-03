package kz.javazhan.sigma_finance.domain.factories.impl.accountsFactory;

import kz.javazhan.sigma_finance.domain.entities.BankAccount;
import kz.javazhan.sigma_finance.domain.entities.User;
import kz.javazhan.sigma_finance.domain.enums.AccountType;
import kz.javazhan.sigma_finance.domain.enums.CurrencyTypeEnum;
import kz.javazhan.sigma_finance.domain.enums.AccountStatusEnum;
import kz.javazhan.sigma_finance.domain.factories.AccountFactory;

public class CurrentAccountFactory implements AccountFactory {
    @Override
    public BankAccount createAccount(String accountNumber, long initialBalance, CurrencyTypeEnum currency, AccountStatusEnum status, User owner) {
        return BankAccount.builder()
                .accountNumber(accountNumber)
                .accountType(AccountType.CURRENT)
                .balance(initialBalance)
                .currency(currency)
                .status(status)
                .owner(owner)
                .build();
    }

    @Override
    public AccountType getAccountType() {
        return AccountType.CURRENT;
    }
}

