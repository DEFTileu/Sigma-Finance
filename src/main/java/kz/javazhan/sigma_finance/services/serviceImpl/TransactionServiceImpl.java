package kz.javazhan.sigma_finance.services.serviceImpl;

import kz.javazhan.sigma_finance.domain.DTOS.SelfTransferRequestDTO;
import kz.javazhan.sigma_finance.domain.DTOS.TransactionHistoryDTO;
import kz.javazhan.sigma_finance.domain.DTOS.TransferRequestDTO;
import kz.javazhan.sigma_finance.domain.entities.BankAccount;
import kz.javazhan.sigma_finance.domain.entities.Transaction;
import kz.javazhan.sigma_finance.domain.entities.User;
import kz.javazhan.sigma_finance.domain.enums.AccountType;
import kz.javazhan.sigma_finance.domain.enums.TransactionDirection;
import kz.javazhan.sigma_finance.domain.enums.TransactionStatus;
import kz.javazhan.sigma_finance.domain.enums.TransactionType;
import kz.javazhan.sigma_finance.domain.factories.TransactionFactory;
import kz.javazhan.sigma_finance.domain.factories.TransactionFactoryProvider;
import kz.javazhan.sigma_finance.exceptions.TransactionException;
import kz.javazhan.sigma_finance.repositories.AccountRepository;
import kz.javazhan.sigma_finance.repositories.TransactionRepository;
import kz.javazhan.sigma_finance.services.CurrencyService;
import kz.javazhan.sigma_finance.services.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountRepository bankAccountRepository;
    private final CurrencyService currencyService;

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Transaction transfer(BankAccount fromAccount, BankAccount toAccount, Long amount, String description) {
        Transaction transaction = null;
        try {
            TransactionFactory factory = TransactionFactoryProvider.getFactory(fromAccount.getAccountType());
            transaction = factory.createTransferOperation()
                    .execute(fromAccount, toAccount, amount, description);

            fromAccount.setBalance(fromAccount.getBalance() - amount);
            toAccount.setBalance(toAccount.getBalance() + amount);

            bankAccountRepository.save(fromAccount);
            bankAccountRepository.save(toAccount);

            transaction.setStatus(TransactionStatus.COMPLETED);
            Transaction savedTransaction = transactionRepository.save(transaction);
            log.info("Перевод выполнен успешно: с {} на {} сумма {}",
                    fromAccount.getAccountNumber(),
                    toAccount.getAccountNumber(),
                    amount);
            return savedTransaction;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.setStatus(TransactionStatus.FAILED);
            }
            log.error("Ошибка при выполнении перевода: {}", e.getMessage());
            throw new TransactionException("Ошибка при выполнении перевода: " + e.getMessage(), e);
        }
    }

    @Transactional
    @Override
    public TransactionHistoryDTO transferByPhoneAndReturnHistory(TransferRequestDTO transactionDTO, long userId) {
        BankAccount fromAccount = bankAccountRepository.findByOwnerAndAccountType(
                User.builder().id(transactionDTO.getSourceAccountId()).build(),
                AccountType.CURRENT)
                .getFirst();
//        if (transactionDTO.isUseBonuses()){
//            BankAccount bonusAccount = bankAccountRepository.findByOwnerAndAccountType(
//                    User.builder().id(userId).build(),
//                    AccountType.BONUS)
//                    .getFirst();
//            fromAccount.setBalance(fromAccount.getBalance() - bonusAccount.getBalance());
//        }

        log.info("From account: {}", fromAccount);
        BankAccount toAccount = bankAccountRepository.findByOwnerAndAccountType(
                User.builder().id(transactionDTO.getDestinationAccountId()).build(),
                AccountType.CURRENT)
                .getFirst();
        log.info("To account: {}", toAccount);
        Transaction transaction = transfer(fromAccount, toAccount, transactionDTO.getAmount(), transactionDTO.getDescription());
        return mapToHistoryDTO(transaction, List.of(fromAccount, toAccount));
    }

    @Override
    public TransactionHistoryDTO selfTransferFromDTOAndReturnHistory(SelfTransferRequestDTO transferDTO, long userId) {
        BankAccount fromAccount = bankAccountRepository.findById(transferDTO.getFromAccountId())
                .orElseThrow(() -> new TransactionException("Счет отправителя не найден с ID: " + transferDTO.getFromAccountId()));
        BankAccount toAccount = bankAccountRepository.findById(transferDTO.getToAccountId())
                .orElseThrow(() -> new TransactionException("Счет получателя не найден с ID: " + transferDTO.getToAccountId()));

        Transaction transaction = selfTransfer(fromAccount, toAccount, transferDTO.getAmount(), "Перевод между собственными счетами");
        return mapToHistoryDTO(transaction, List.of(fromAccount, toAccount));
    }


    @Override
    public List<Transaction> getTransactionsByUserId(Long id) {
        List<Transaction> transactions = transactionRepository.historyOfTransactions(id);
        log.info("Найдено {} транзакций для пользователя с ID {}", transactions.size(), id);
        return transactions;
    }

    @Override
    public List<TransactionHistoryDTO> getTransactionHistory(Long userId, TransactionType filterType) {
        List<BankAccount> userAccounts = bankAccountRepository.findAllByOwner(
                User.builder().id(userId).build()
        );

        List<Transaction> transactions = transactionRepository.historyOfTransactions(userId);

        if (filterType != null) {
            transactions = transactions.stream()
                    .filter(t -> t.getTransactionType() == filterType)
                    .toList();
        }

        return transactions.stream()
                .map(transaction -> mapToHistoryDTO(transaction, userAccounts))
                .sorted((t1, t2) -> t2.getCreatedAt().compareTo(t1.getCreatedAt()))
                .toList();
    }

    @Override
    public TransactionHistoryDTO getTransactionByIdAndUserId(UUID transactionId, Long userId) {
        List<BankAccount> userAccounts = bankAccountRepository.findAllByOwner(
                User.builder().id(userId).build()
        );

        Transaction transaction = transactionRepository.findByIdAndUserId(transactionId, userId)
                .orElseThrow(() -> new TransactionException("Транзакция с ID " + transactionId + " не найдена для пользователя с ID " + userId));

        log.info("Найдена транзакция с ID {} для пользователя с ID {}", transactionId, userId);

        return mapToHistoryDTO(transaction, userAccounts);
    }

    @Override
    public Transaction selfTransfer(BankAccount fromAccount, BankAccount toAccount, Long amount, String description) {
        //lets check if accounts belong to the same user
        if (!fromAccount.getOwner().getId().equals(toAccount.getOwner().getId())) {
            throw new TransactionException("Перевод на собственный счет возможен только между счетами одного пользователя.");
        }
        if (fromAccount.getId().equals(toAccount.getId())) {
            throw new TransactionException("Нельзя перевести на тот же самый счет.");
        }
        if (fromAccount.getBalance() < amount) {
            throw new TransactionException("Недостаточно средств на счете для перевода.");
        }
        if (fromAccount.getCurrency().equals(toAccount.getCurrency())) {
            return transfer(fromAccount, toAccount, amount, description);

        } else {
            long convertedAmount = currencyService.convertAmount(
                    fromAccount.getCurrency(),
                    toAccount.getCurrency(),
                    amount
            );
            return transfer(fromAccount, toAccount, amount, description + " (Конвертация: " + convertedAmount + " " + toAccount.getCurrency() + ")");

        }
    }

    private TransactionHistoryDTO mapToHistoryDTO(Transaction transaction, List<BankAccount> userAccounts) {
        TransactionHistoryDTO dto = new TransactionHistoryDTO();

        dto.setId(transaction.getId());
        dto.setType(transaction.getTransactionType());
        dto.setStatus(transaction.getStatus());
        dto.setCurrency(transaction.getCurrency());
        dto.setDescription(transaction.getDescription());
        dto.setCreatedAt(transaction.getCreatedAt());
        dto.setUpdatedAt(transaction.getUpdatedAt());

        dto.setSourceAccountId(transaction.getSourceAccount().getId());
        dto.setSourceAccountNumber(maskAccountNumber(transaction.getSourceAccount().getAccountNumber()));
        dto.setTargetAccountId(transaction.getTargetAccount().getId());
        dto.setTargetAccountNumber(maskAccountNumber(transaction.getTargetAccount().getAccountNumber()));

        boolean isUserSource = userAccounts.stream()
                .anyMatch(acc -> acc.getId().equals(transaction.getSourceAccount().getId()));
        boolean isUserTarget = userAccounts.stream()
                .anyMatch(acc -> acc.getId().equals(transaction.getTargetAccount().getId()));

        TransactionDirection direction;
        String counterpartyName = null;
        String counterpartyPhone = null;

        switch (transaction.getTransactionType()) {
            case DEPOSIT, BONUS -> {
                direction = TransactionDirection.INCOMING;
                dto.setAmount(Math.abs(transaction.getAmount()));
            }
            case WITHDRAW -> {
                direction = TransactionDirection.OUTGOING;
                dto.setAmount(Math.abs(transaction.getAmount()));
            }
            case TRANSFER -> {
                if (isUserSource && !isUserTarget) {
                    direction = TransactionDirection.OUTGOING;
                    dto.setAmount(Math.abs(transaction.getAmount()));
                    User recipient = transaction.getTargetAccount().getOwner();
                    counterpartyName = recipient.getName() + " " + recipient.getSurname();
                    counterpartyPhone = recipient.getPhoneNumber();
                } else if (!isUserSource && isUserTarget) {
                    direction = TransactionDirection.INCOMING;
                    dto.setAmount(Math.abs(transaction.getAmount()));
                    User sender = transaction.getSourceAccount().getOwner();
                    counterpartyName = sender.getName() + " " + sender.getSurname();
                    counterpartyPhone = sender.getPhoneNumber();
                } else {
                    direction = TransactionDirection.OUTGOING;
                    dto.setAmount(Math.abs(transaction.getAmount()));
                    counterpartyName = "Собственный счет";
                }
            }
            default -> {
                direction = TransactionDirection.OUTGOING;
                dto.setAmount(Math.abs(transaction.getAmount()));
            }
        }

        dto.setDirection(direction);
        dto.setCounterpartyName(counterpartyName);
        dto.setCounterpartyPhone(counterpartyPhone);

        return dto;
    }

    private String maskAccountNumber(String accountNumber) {
        if (accountNumber == null || accountNumber.length() <= 4) {
            return accountNumber;
        }
        return "•••• " + accountNumber.substring(accountNumber.length() - 4);
    }

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
            long newBalance = account.getBalance() + transaction.getAmount();

            account.setBalance(newBalance);
            bankAccountRepository.save(account);

            transaction.setStatus(TransactionStatus.COMPLETED);
            Transaction savedTransaction = transactionRepository.save(transaction);
            log.info("Транзакция успешно выполнена: {} на сумма {}",
                    savedTransaction.getTransactionType(),
                    Math.abs(savedTransaction.getAmount()));
            return savedTransaction;
        } catch (Exception e) {
            try {
                transaction.setStatus(TransactionStatus.FAILED);
            } catch (Exception ignored) {}
            log.error("Ошибка при выполнении транзакции: {}", e.getMessage());
            throw new TransactionException("Ошибка при выполнении транзакции: " + e.getMessage(), e);
        }
    }
}
