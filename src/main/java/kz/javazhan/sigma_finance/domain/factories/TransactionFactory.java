package kz.javazhan.sigma_finance.domain.factories;

import kz.javazhan.sigma_finance.domain.enums.AccountType;

public interface TransactionFactory {
    TransactionOperation createDepositOperation();
    TransactionOperation createWithdrawOperation();
    TransferOperation createTransferOperation();
    AccountType getAccountType();
}
