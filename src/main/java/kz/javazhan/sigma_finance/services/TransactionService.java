package kz.javazhan.sigma_finance.services;

import kz.javazhan.sigma_finance.domain.DTOS.TransactionHistoryDTO;
import kz.javazhan.sigma_finance.domain.DTOS.TransferRequestDTO;
import kz.javazhan.sigma_finance.domain.entities.BankAccount;
import kz.javazhan.sigma_finance.domain.entities.Transaction;
import kz.javazhan.sigma_finance.domain.enums.TransactionType;

import java.util.List;

public interface TransactionService {
    Transaction deposit(BankAccount account, Long amount, String description);
    Transaction withdraw(BankAccount account, Long amount, String description);
    Transaction executeTransaction(Transaction transaction);
    Transaction transfer(BankAccount fromAccount, BankAccount toAccount, Long amount, String description);
    Transaction transferByPhone(TransferRequestDTO transactionDTO);

    List<Transaction> getTransactionsByUserId(Long id);
    List<TransactionHistoryDTO> getTransactionHistory(Long userId, TransactionType filterType);
}
