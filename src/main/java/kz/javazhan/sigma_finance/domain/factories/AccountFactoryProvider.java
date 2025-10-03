package kz.javazhan.sigma_finance.domain.factories;

import kz.javazhan.sigma_finance.domain.enums.AccountType;
import kz.javazhan.sigma_finance.domain.factories.impl.accountsFactory.BonusAccountFactory;
import kz.javazhan.sigma_finance.domain.factories.impl.accountsFactory.CurrentAccountFactory;
import kz.javazhan.sigma_finance.domain.factories.impl.accountsFactory.SavingsAccountFactory;

import java.util.HashMap;
import java.util.Map;

public class AccountFactoryProvider {
    private static final Map<AccountType, AccountFactory> factories = new HashMap<>();

    static {
        factories.put(AccountType.CURRENT, new CurrentAccountFactory());
        factories.put(AccountType.SAVINGS, new SavingsAccountFactory());
        factories.put(AccountType.BONUS, new BonusAccountFactory());
    }

    public static AccountFactory getFactory(AccountType type) {
        return factories.get(type);
    }
}

