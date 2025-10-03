package kz.javazhan.sigma_finance.domain.factories.impl.transactions;

import kz.javazhan.sigma_finance.domain.enums.AccountType;
import kz.javazhan.sigma_finance.domain.factories.TransactionFactory;
import kz.javazhan.sigma_finance.domain.factories.TransactionOperation;
import kz.javazhan.sigma_finance.domain.factories.TransferOperation;

public class CurrentAccountTransactionFactory implements TransactionFactory {
    @Override
    public TransactionOperation createDepositOperation() {
        return new DepositOperation();
    }

    @Override
    public TransactionOperation createWithdrawOperation() {
        return new WithdrawOperation();
    }

    @Override
    public TransferOperation createTransferOperation() {
        return new TransferOperationImpl();
    }

    @Override
    public AccountType getAccountType() {
        return AccountType.CURRENT;
    }
}
