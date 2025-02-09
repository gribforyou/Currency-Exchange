package dao;

public record ExchangeRateDao(
        Integer id,
        CurrencyDao baseCurrency,
        CurrencyDao targetCurrency,
        double rate
) {
}
