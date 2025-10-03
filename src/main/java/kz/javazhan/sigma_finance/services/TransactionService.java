package kz.javazhan.sigma_finance.services;

import kz.javazhan.sigma_finance.domain.entities.BankAccount;
import kz.javazhan.sigma_finance.domain.entities.Transaction;

public interface TransactionService {
    Transaction deposit(BankAccount account, Long amount, String description);
    Transaction withdraw(BankAccount account, Long amount, String description);
    Transaction executeTransaction(Transaction transaction);
}
