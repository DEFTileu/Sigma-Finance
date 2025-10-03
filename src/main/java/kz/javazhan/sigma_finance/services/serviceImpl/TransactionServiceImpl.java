package kz.javazhan.sigma_finance.services.serviceImpl;

import kz.javazhan.sigma_finance.domain.entities.BankAccount;
import kz.javazhan.sigma_finance.domain.entities.Transaction;
import kz.javazhan.sigma_finance.domain.factories.TransactionFactory;
import kz.javazhan.sigma_finance.domain.factories.TransactionFactoryProvider;
import kz.javazhan.sigma_finance.exceptions.TransactionException;
import kz.javazhan.sigma_finance.repositories.AccountRepository;
import kz.javazhan.sigma_finance.repositories.TransactionRepository;
import kz.javazhan.sigma_finance.services.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountRepository bankAccountRepository;

    @Override
    @Transactional
    public Transaction deposit(BankAccount account, Long amount, String description) {
        try {
            TransactionFactory factory = TransactionFactoryProvider.getFactory(account.getAccountType());
            Transaction transaction = factory.createDepositOperation()
                    .execute(account, amount, description);

            return executeTransaction(transaction);
        } catch (Exception e) {
            log.error("Ошибка при выполнении депозита: {}", e.getMessage());
            throw new TransactionException("Ошибка при выполнении депозита: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public Transaction withdraw(BankAccount account, Long amount, String description) {
        try {
            TransactionFactory factory = TransactionFactoryProvider.getFactory(account.getAccountType());
            Transaction transaction = factory.createWithdrawOperation()
                    .execute(account, amount, description);

            return executeTransaction(transaction);
        } catch (Exception e) {
            log.error("Ошибка при выполнении снятия: {}", e.getMessage());
            throw new TransactionException("Ошибка при выполнении снятия: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public Transaction executeTransaction(Transaction transaction) {
        try {
            BankAccount account = transaction.getSourceAccount();
            Long newBalance = account.getBalance() + transaction.getAmount();

            account.setBalance(newBalance);
            bankAccountRepository.save(account);

            Transaction savedTransaction = transactionRepository.save(transaction);
            log.info("Транзакция успешно выполнена: {} на сумму {}",
                    savedTransaction.getTransactionType(),
                    Math.abs(savedTransaction.getAmount()));
            return savedTransaction;
        } catch (Exception e) {
            log.error("Ошибка при выполнении транзакции: {}", e.getMessage());
            throw new TransactionException("Ошибка при выполнении транзакции: " + e.getMessage(), e);
        }
    }
}
