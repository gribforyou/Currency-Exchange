package model;

import dao.CurrencyDao;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public final class ModelManager {
    private ModelManager() {
    }

    public static CurrencyDao addCurrency(CurrencyDao currency) {
        final String name = currency.name();
        final String code = currency.code();
        final String sign = currency.sign();

        final String sql = """
                INSERT INTO currencies (full_name, code, sign)
                VALUES ('%s', '%s', '%s');
                """.formatted(name, code, sign);

        try (Connection connection = ConnectionManager.getConnection();
             Statement statement = connection.createStatement()) {

            var result = statement.executeUpdate(sql);

            return new CurrencyDao(result, name, code, sign);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<CurrencyDao> getCurrencies(int limit) {
        final String sql = """
                SELECT id, full_name, code, sign FROM currencies
                LIMIT %d ;
                """.formatted(limit);

        List<CurrencyDao> currencies = new ArrayList<>();

        try (Connection connection = ConnectionManager.getConnection();
             Statement statement = connection.createStatement()) {
            var result = statement.executeQuery(sql);
            while (result.next()) {
                CurrencyDao addedCurrency = new CurrencyDao(result.getInt("id"),
                        result.getString("full_name"), result.getString("code"),
                        result.getString("sign"));

                currencies.add(addedCurrency);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return currencies;
    }
}
