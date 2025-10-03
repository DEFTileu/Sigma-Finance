package kz.javazhan.sigma_finance.domain.factories;

import kz.javazhan.sigma_finance.domain.enums.AccountType;
import kz.javazhan.sigma_finance.domain.factories.impl.transactions.BonusAccountTransactionFactory;
import kz.javazhan.sigma_finance.domain.factories.impl.transactions.CurrentAccountTransactionFactory;
import kz.javazhan.sigma_finance.domain.factories.impl.transactions.SavingsAccountTransactionFactory;

import java.util.HashMap;
import java.util.Map;

public class TransactionFactoryProvider {
    private static final Map<AccountType, TransactionFactory> factories = new HashMap<>();

    static {
        factories.put(AccountType.CURRENT, new CurrentAccountTransactionFactory());
        factories.put(AccountType.SAVINGS, new SavingsAccountTransactionFactory());
        factories.put(AccountType.BONUS, new BonusAccountTransactionFactory());
    }

    public static TransactionFactory getFactory(AccountType type) {
        return factories.get(type);
    }
}
