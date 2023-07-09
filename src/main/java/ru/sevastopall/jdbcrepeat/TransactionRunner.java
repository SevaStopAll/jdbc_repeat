package ru.sevastopall.jdbcrepeat;

import ru.sevastopall.jdbcrepeat.util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransactionRunner {
    public static void main(String[] args) throws SQLException, InterruptedException {
        long flightId = 9;
        var deleteTicketSql = "DELETE FROM ticket WHERE flight_id = ?";
       var deleteFlightSql = "DELETE FROM flight WHERE id = ?";
        Connection connection = null;
        PreparedStatement deleteFlightStatement = null;
        PreparedStatement deleteTicketStatement = null;
        try {
            connection = ConnectionManager.get();
            deleteFlightStatement = connection.prepareStatement(deleteFlightSql);
            deleteTicketStatement = connection.prepareStatement(deleteTicketSql);

            connection.setAutoCommit(false);
            deleteFlightStatement.setLong(1, flightId);
            deleteTicketStatement.setLong(1, flightId);

            var deletedTicketResults = deleteTicketStatement.executeUpdate();
            var deletedFlightResults = deleteFlightStatement.executeUpdate();
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
            if (deleteFlightStatement != null) {
                deleteFlightStatement.close();
            }
            if (deleteTicketStatement != null) {
                deleteTicketStatement.close();
            }
        }
    }
}
