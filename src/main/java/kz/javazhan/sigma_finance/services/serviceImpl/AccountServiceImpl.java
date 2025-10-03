package kz.javazhan.sigma_finance.services.serviceImpl;

import kz.javazhan.sigma_finance.domain.entities.Account;
import kz.javazhan.sigma_finance.domain.entities.BankAccount;
import kz.javazhan.sigma_finance.domain.entities.User;
import kz.javazhan.sigma_finance.domain.enums.AccountStatusEnum;
import kz.javazhan.sigma_finance.domain.enums.AccountType;
import kz.javazhan.sigma_finance.domain.enums.CurrencyTypeEnum;
import kz.javazhan.sigma_finance.domain.factories.AccountFactory;
import kz.javazhan.sigma_finance.domain.factories.AccountFactoryProvider;
import kz.javazhan.sigma_finance.repositories.AccountRepository;
import kz.javazhan.sigma_finance.services.AccountService;
import kz.javazhan.sigma_finance.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final UserService userService;


    public static String generateAccountNumber() {
        StringBuilder accountNumber = new StringBuilder("40817810");
        for (int i = 0; i < 10; i++) {
            int digit = (int) (Math.random() * 10);
            accountNumber.append(digit);
        }
        return accountNumber.toString();
    }

    @Override
    public List<BankAccount> createDefoultAccount2NewUser(User user) {
        AccountFactory currentFactory = AccountFactoryProvider.getFactory(AccountType.CURRENT);
        BankAccount currentAccount = currentFactory.createAccount(
                generateAccountNumber(),
                0L,
                CurrencyTypeEnum.KZT,
                AccountStatusEnum.ACTIVE,
                user
        );

        AccountFactory bonusFactory = AccountFactoryProvider.getFactory(AccountType.BONUS);
        BankAccount bonusAccount;

        bonusAccount = bonusFactory.createAccount(
                generateAccountNumber(),
                0L,
                CurrencyTypeEnum.KZT,
                AccountStatusEnum.ACTIVE,
                user
        );


        List<BankAccount> bankAccounts = new ArrayList<>();
        bankAccounts.add(currentAccount);
        bankAccounts.add(bonusAccount);
        return accountRepository.saveAll(bankAccounts);
    }

    @Override
    public BankAccount save(BankAccount account) {
        return  accountRepository.save(account);
    }

    public Account displayInfo(Account account) {
        System.out.println("Account ID: " + account.getId());
        System.out.println("Account Number: " + account.getAccountNumber());
        System.out.println("Account Type: " + account.getAccountType());
        System.out.println("Account Status: " + account.getStatus());
        System.out.println("Account Balance: " + account.getBalance());
        return account;
    }


}
