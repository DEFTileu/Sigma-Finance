package kz.javazhan.sigma_finance.domain.factories.impl.transactions;

import kz.javazhan.sigma_finance.domain.entities.BankAccount;
import kz.javazhan.sigma_finance.domain.entities.Transaction;
import kz.javazhan.sigma_finance.domain.enums.AccountStatusEnum;
import kz.javazhan.sigma_finance.domain.enums.TransactionStatus;
import kz.javazhan.sigma_finance.domain.enums.TransactionType;
import kz.javazhan.sigma_finance.domain.factories.TransactionOperation;
import kz.javazhan.sigma_finance.exceptions.TransactionException;

public class DepositOperation implements TransactionOperation {
    @Override
    public Transaction execute(BankAccount account, Long amount, String description) {
        if (account == null) {
            throw new TransactionException("Банковский счет не может быть null");
        }
        if (amount == null || amount <= 0) {
            throw new TransactionException("Сумма депозита должна быть положительной");
        }
        if (!account.getStatus().equals(AccountStatusEnum.ACTIVE)) {
            throw new TransactionException("Невозможно выполнить депозит на неактивный счет");
        }

        return Transaction.builder()
                .transactionType(TransactionType.DEPOSIT)
                .amount(amount)
                .sourceAccount(account)
                .targetAccount(account)
                .status(TransactionStatus.PENDING)
                .currency(account.getCurrency())
                .description(description)
                .build();
    }
}
