package kz.javazhan.sigma_finance.services.serviceImpl;

import kz.javazhan.sigma_finance.domain.DTOS.ExchangeRateResponse;
import kz.javazhan.sigma_finance.domain.enums.CurrencyTypeEnum;
import kz.javazhan.sigma_finance.services.CurrencyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {
    private final RestTemplate restTemplate;

    @Value("${currency.apiKey}")
    private String apiKey;


    @Override
    public double getExchangeRate(CurrencyTypeEnum fromCurrency, CurrencyTypeEnum toCurrency) {
        String url = "https://v6.exchangerate-api.com/v6/" + apiKey + "/latest/" + fromCurrency.name();
        // Не логируем полный URL, чтобы не раскрывать apiKey
        log.info("Fetching exchange rate base={} target={}", fromCurrency, toCurrency);

        try {
            ExchangeRateResponse response = restTemplate.getForObject(url, ExchangeRateResponse.class);

            if (response != null && "success".equals(response.getResult())) {
                Double rate = response.getConversionRates().get(toCurrency.name());
                if (rate != null) {
                    return rate;
                } else {
                    log.error("Exchange rate for {} to {} not found", fromCurrency, toCurrency);
                }
            } else {
                log.error("Failed to fetch exchange rates: {}", response != null ? response.getErrorType() : "null response");
            }
        } catch (Exception e) {
            log.error("Error while fetching exchange rates", e);
        }

        return 0.0;
    }


    @Override
    public long convertAmount(CurrencyTypeEnum fromCurrency, CurrencyTypeEnum toCurrency, Long amount) {
        return 0;
    }
}
