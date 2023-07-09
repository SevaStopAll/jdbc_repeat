package ru.sevastopall.jdbcrepeat;

import ru.sevastopall.jdbcrepeat.util.ConnectionManager;

import java.sql.Connection;
import java.sql.SQLException;

public class BatchTransactionRunner {
    public static void main(String[] args) throws SQLException, InterruptedException {
        long flightId = 9;
        var deleteTicketSql = "DELETE FROM ticket WHERE flight_id = " + flightId;
        var deleteFlightSql = "DELETE FROM flight WHERE id = " + flightId;
        Connection connection = null;
        var statement = connection.createStatement();
        try {
            connection = ConnectionManager.get();
            connection.setAutoCommit(false);

            statement = connection.createStatement();
            statement.addBatch(deleteTicketSql);
            statement.addBatch(deleteFlightSql);
            int[] results = statement.executeBatch();
            connection.commit();

        } catch (Exception e) {
            if (connection != null) {
                connection.rollback();
            }
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (statement != null) {
                statement.close();
            }
        }
    }
}
