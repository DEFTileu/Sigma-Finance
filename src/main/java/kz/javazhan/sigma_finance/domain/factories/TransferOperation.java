package kz.javazhan.sigma_finance.domain.factories;

import kz.javazhan.sigma_finance.domain.entities.BankAccount;
import kz.javazhan.sigma_finance.domain.entities.Transaction;

public interface TransferOperation {
    Transaction execute(BankAccount fromAccount, BankAccount toAccount, Long amount, String description);
}
