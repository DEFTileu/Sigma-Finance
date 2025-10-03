package kz.javazhan.sigma_finance.repositories;

import kz.javazhan.sigma_finance.domain.entities.BankAccount;
import kz.javazhan.sigma_finance.domain.entities.User;
import kz.javazhan.sigma_finance.domain.enums.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<BankAccount, UUID> {
    List<BankAccount> findByOwnerAndAccountType(User owner, AccountType accountType);

    List<BankAccount> findAllByOwner(User owner);

}
