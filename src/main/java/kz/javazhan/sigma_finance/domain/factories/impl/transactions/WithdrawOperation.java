package kz.javazhan.sigma_finance.domain.factories.impl.transactions;

import kz.javazhan.sigma_finance.domain.entities.BankAccount;
import kz.javazhan.sigma_finance.domain.entities.Transaction;
import kz.javazhan.sigma_finance.domain.enums.AccountStatusEnum;
import kz.javazhan.sigma_finance.domain.enums.TransactionStatus;
import kz.javazhan.sigma_finance.domain.enums.TransactionType;
import kz.javazhan.sigma_finance.domain.factories.TransactionOperation;
import kz.javazhan.sigma_finance.exceptions.TransactionException;

public class WithdrawOperation implements TransactionOperation {
    @Override
    public Transaction execute(BankAccount account, Long amount, String description) {
        if (account == null) {
            throw new TransactionException("Банковский счет не может быть null");
        }
        if (amount == null || amount <= 0) {
            throw new TransactionException("Сумма снятия должна быть положительной");
        }
        if (!account.getStatus().equals(AccountStatusEnum.ACTIVE)) {
            throw new TransactionException("Невозможно выполнить снятие со неактивного счета");
        }
        if (account.getBalance() < amount) {
            throw new TransactionException("Недостаточно средств на счете");
        }

        return Transaction.builder()
                .transactionType(TransactionType.WITHDRAW)
                .amount(-amount)
                .sourceAccount(account)
                .targetAccount(account)
                .status(TransactionStatus.PENDING)
                .currency(account.getCurrency())
                .description(description)
                .build();
    }
}
