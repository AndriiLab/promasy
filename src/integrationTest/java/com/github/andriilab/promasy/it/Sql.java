package com.github.andriilab.promasy.it;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Plain SQL through the superuser connection: lets tests look at the rows behind Hibernate's back, and change them.
 */
public final class Sql {

    private Sql() {
    }

    /** First column of the first row, or {@code null} if the query returns no rows. */
    public static Object scalar(String sql, Object... params) {
        try (Connection connection = PostgresContainerSupport.superuserConnection();
             PreparedStatement statement = prepare(connection, sql, params);
             ResultSet resultSet = statement.executeQuery()) {
            return resultSet.next() ? resultSet.getObject(1) : null;
        } catch (SQLException e) {
            throw new IllegalStateException(sql, e);
        }
    }

    public static long count(String sql, Object... params) {
        return ((Number) scalar(sql, params)).longValue();
    }

    public static void execute(String sql, Object... params) {
        try (Connection connection = PostgresContainerSupport.superuserConnection();
             PreparedStatement statement = prepare(connection, sql, params)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(sql, e);
        }
    }

    private static PreparedStatement prepare(Connection connection, String sql, Object[] params) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(sql);
        for (int i = 0; i < params.length; i++) {
            statement.setObject(i + 1, params[i]);
        }
        return statement;
    }
}
