package kz.javazhan.sigma_finance.controllers;

import kz.javazhan.sigma_finance.config.Settings;
import kz.javazhan.sigma_finance.domain.entities.BankAccount;
import kz.javazhan.sigma_finance.domain.entities.User;
import kz.javazhan.sigma_finance.domain.enums.AccountType;
import kz.javazhan.sigma_finance.repositories.AccountRepository;
import kz.javazhan.sigma_finance.services.AccountService;
import kz.javazhan.sigma_finance.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final UserService userService;
    private final AccountRepository accountRepository;
    private final AccountService accountService;

    @GetMapping("")
    public List<BankAccount> getMyMainAccount() {
        User user = userService.getCurrentUser();
        return accountRepository.findAllByOwner(user).stream().map(
                account -> {
                    account.setOwner(null);
                    return account;
                }
        ).toList();
    }

    @GetMapping("/bonus")
    public List<String> getMyBonusAccount() {
        Settings settings = new Settings("some1","some2");
        Settings settings2 = settings.clone();




        User user = userService.getCurrentUser();
        return accountRepository.findByOwnerAndAccountType(user, AccountType.BONUS)
                .stream()
                .map(BankAccount::getAccountNumber)
                .toList();
    }

}
