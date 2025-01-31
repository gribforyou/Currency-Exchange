package model;

import dao.CurrencyDao;
import model.exceptions.LinesNotFoundException;
import model.exceptions.UnavailableDBException;
import model.exceptions.UniqueConstraintFailedException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public final class ModelManager {
    private ModelManager() {
    }

    private final static int UNIQUE_CONSTRAINT_FAILED_CODE = 19;

    public static CurrencyDao addCurrency(CurrencyDao currency) throws UnavailableDBException, SQLException, UniqueConstraintFailedException {
        final String name = currency.name();
        final String code = currency.code();
        final String sign = currency.sign();

        final String sql = """
                INSERT INTO currencies (full_name, code, sign)
                VALUES (?, ?, ?);
                """;

        try (Connection connection = ConnectionManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, code);
            preparedStatement.setString(3, sign);
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            final int id = resultSet.getInt(1);
            return new CurrencyDao(id, name, code, sign);
        } catch (SQLException e) {
            if (e.getErrorCode() == UNIQUE_CONSTRAINT_FAILED_CODE) {
                throw new UniqueConstraintFailedException();
            } else {
                throw new UnavailableDBException();
            }
        }
    }

    public static CurrencyDao getCurrencyByCode(String targetCode) throws LinesNotFoundException, UnavailableDBException {
        final String sql = """
                SELECT id, full_name, code, sign 
                FROM currencies
                WHERE code = ?;
                """;

        try (Connection connection = ConnectionManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, targetCode);

            var result = preparedStatement.executeQuery();

            if (result.next()) {
                final int id = result.getInt(1);
                final String fullName = result.getString(2);
                final String code = result.getString(3);
                final String sign = result.getString(4);

                return new CurrencyDao(id, fullName, code, sign);
            } else {
                throw new LinesNotFoundException();
            }

        } catch (SQLException e) {
            throw new UnavailableDBException();
        }
    }

    public static List<CurrencyDao> getCurrencies(int limit) throws UnavailableDBException, LinesNotFoundException {
        final String sql = """
                SELECT id, full_name, code, sign FROM currencies
                LIMIT ?;
                """;

        List<CurrencyDao> currencies = new ArrayList<>();

        try (Connection connection = ConnectionManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, limit);
            var result = preparedStatement.executeQuery();
            while (result.next()) {
                final int id = result.getInt(1);
                final String fullName = result.getString(2);
                final String code = result.getString(3);
                final String sign = result.getString(4);
                currencies.add(new CurrencyDao(id, fullName, code, sign));
            }

            if (currencies.size() == 0) {
                throw new LinesNotFoundException();
            }

            return currencies;
        } catch (SQLException e) {
            throw new UnavailableDBException();
        }
    }
}
