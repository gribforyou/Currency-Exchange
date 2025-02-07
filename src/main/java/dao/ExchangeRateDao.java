package dao;

public record ExchangeRateDao(
        Integer id,
        String baseCurrencyCode,
        String targetCurrencyCode,
        double exchangeRate
) {
}
