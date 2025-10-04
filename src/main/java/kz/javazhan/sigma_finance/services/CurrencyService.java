package kz.javazhan.sigma_finance.services;

import kz.javazhan.sigma_finance.domain.enums.CurrencyTypeEnum;

public interface CurrencyService {
    double getExchangeRate(CurrencyTypeEnum fromCurrency, CurrencyTypeEnum toCurrency);
    long convertAmount(CurrencyTypeEnum fromCurrency, CurrencyTypeEnum toCurrency, Long amount);
}
