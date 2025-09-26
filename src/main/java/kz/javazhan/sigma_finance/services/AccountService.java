package kz.javazhan.sigma_finance.services;

import kz.javazhan.sigma_finance.domain.entities.BankAccount;
import kz.javazhan.sigma_finance.domain.entities.User;

import java.util.List;

public interface AccountService {
    List<BankAccount> createDefoultAccount2NewUser(User user);
    BankAccount save(BankAccount account);
}
