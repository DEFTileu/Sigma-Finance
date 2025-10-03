package kz.javazhan.sigma_finance.repositories;

import kz.javazhan.sigma_finance.domain.entities.BankAccount;
import kz.javazhan.sigma_finance.domain.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    List<Transaction> findAllByTargetAccountOrSourceAccount(BankAccount targetAccount, BankAccount sourceAccount);

    @Query("SELECT t FROM Transaction t JOIN BankAccount b ON t.sourceAccount = b OR t.targetAccount = b WHERE b.owner.id = :userId")
    List<Transaction> historyOfTransactions(Long userId);
}
