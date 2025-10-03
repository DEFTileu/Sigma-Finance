package kz.javazhan.sigma_finance.domain.factories;

import kz.javazhan.sigma_finance.domain.entities.BankAccount;
import kz.javazhan.sigma_finance.domain.entities.Transaction;

public interface TransactionOperation {
    Transaction execute(BankAccount account, Long amount, String description);
}
