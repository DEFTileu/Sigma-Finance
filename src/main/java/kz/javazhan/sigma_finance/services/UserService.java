package kz.javazhan.sigma_finance.services;

import kz.javazhan.sigma_finance.domain.entities.BankAccount;
import kz.javazhan.sigma_finance.domain.entities.User;

public interface UserService {
    public User getCurrentUser();

    public User findUserByPhone(String phone);
}
