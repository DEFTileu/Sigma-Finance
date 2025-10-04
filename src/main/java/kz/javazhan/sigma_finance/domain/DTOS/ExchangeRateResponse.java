package kz.javazhan.sigma_finance.domain.DTOS;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;



@Data
public class ExchangeRateResponse {

    private String result;
    private String errorType;
    private Map<String, Double> conversionRates;

    @JsonProperty("result")
    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @JsonProperty("error-type")
    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    @JsonProperty("conversion_rates")
    public Map<String, Double> getConversionRates() {
        return conversionRates;
    }

    public void setConversionRates(Map<String, Double> conversionRates) {
        this.conversionRates = conversionRates;
    }
}
