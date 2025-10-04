package kz.javazhan.sigma_finance.services;


import kz.javazhan.sigma_finance.domain.DTOS.SelfTransferRequestDTO;
import kz.javazhan.sigma_finance.domain.DTOS.TransactionHistoryDTO;
import kz.javazhan.sigma_finance.domain.DTOS.TransferRequestDTO;
import kz.javazhan.sigma_finance.domain.entities.BankAccount;
import kz.javazhan.sigma_finance.domain.entities.Transaction;
import kz.javazhan.sigma_finance.domain.enums.TransactionType;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface TransactionService {
    Transaction selfTransfer(BankAccount fromAccount, BankAccount toAccount, Long amount, String description);

    Transaction deposit(BankAccount account, Long amount, String description);
    Transaction withdraw(BankAccount account, Long amount, String description);
    Transaction executeTransaction(Transaction transaction);
    Transaction transfer(BankAccount fromAccount, BankAccount toAccount, Long amount, String description);
    TransactionHistoryDTO transferByPhoneAndReturnHistory(TransferRequestDTO transactionDTO, long userId);
    TransactionHistoryDTO selfTransferFromDTOAndReturnHistory(SelfTransferRequestDTO selfTransferDTO, long userId);

    List<Transaction> getTransactionsByUserId(Long id);
    List<TransactionHistoryDTO> getTransactionHistory(Long userId, TransactionType filterType);

    TransactionHistoryDTO getTransactionByIdAndUserId(UUID transactionId, Long userId);


}
