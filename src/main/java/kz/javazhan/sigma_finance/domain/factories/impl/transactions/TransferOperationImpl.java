package kz.javazhan.sigma_finance.domain.factories.impl.transactions;

import kz.javazhan.sigma_finance.domain.entities.BankAccount;
import kz.javazhan.sigma_finance.domain.entities.Transaction;
import kz.javazhan.sigma_finance.domain.enums.TransactionStatus;
import kz.javazhan.sigma_finance.domain.enums.TransactionType;
import kz.javazhan.sigma_finance.domain.factories.TransferOperation;
import kz.javazhan.sigma_finance.exceptions.TransactionException;

public class TransferOperationImpl implements TransferOperation {

    @Override
    public Transaction execute(BankAccount fromAccount, BankAccount toAccount, Long amount, String description) {
        validateTransfer(fromAccount, toAccount, amount);

        return Transaction.builder()
                .transactionType(TransactionType.TRANSFER)
                .amount(-amount)
                .sourceAccount(fromAccount)
                .targetAccount(toAccount)
                .status(TransactionStatus.PENDING)
                .currency(fromAccount.getCurrency())
                .description(description)
                .build();
    }

    private void validateTransfer(BankAccount fromAccount, BankAccount toAccount, Long amount) {
        if (fromAccount == null || toAccount == null) {
            throw new TransactionException("Счета для перевода не могут быть null");
        }
        if (amount == null || amount <= 0) {
            throw new TransactionException("Сумма перевода должна быть положительной");
        }
        if (!fromAccount.isActive() || !toAccount.isActive()) {
            throw new TransactionException("Все счета должны быть активны для перевода");
        }
        if (fromAccount.getBalance() < amount) {
            throw new TransactionException("Недостаточно средств на счете для перевода");
        }
        if (fromAccount.equals(toAccount)) {
            throw new TransactionException("Невозможно сделать перевод на тот же счет");
        }
        if (!fromAccount.getCurrency().equals(toAccount.getCurrency())) {
            throw new TransactionException("Перевод возможен только между счетами одной валюты");
        }
    }
}
