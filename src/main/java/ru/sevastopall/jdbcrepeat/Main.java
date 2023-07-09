package ru.sevastopall.jdbcrepeat;

import ru.sevastopall.jdbcrepeat.util.ConnectionManager;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) throws SQLException, InterruptedException {
        try {
        checkMetaData();
        } finally {
            ConnectionManager.closePool();
        }
    }

    private static List<Long> getFlightBetween(LocalDateTime start, LocalDateTime end) throws SQLException, InterruptedException {
        String sql = """
                SELECT id 
                FROM flight
                WHERE departure_date BETWEEN ? AND ?
                """;
        List<Long> result = new ArrayList<>();
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(sql)) {
            System.out.println(statement);
            statement.setFetchSize(50);
            statement.setTimestamp(1, Timestamp.valueOf(start));
            System.out.println(statement);
            statement.setTimestamp(2, Timestamp.valueOf(end));
            System.out.println(statement);
            var resultSet = statement.executeQuery();
            while(resultSet.next()) {
            result.add(resultSet.getLong("id"));
            }
            return result;
        }
    }

    private static List<Long> getTicketsByFlightId(Long flightId) throws SQLException, InterruptedException {
        String sql = """
                SELECT id 
                FROM ticket
                WHERE flight_id = ?
                """;
        List<Long> result = new ArrayList<>();
        try (var connection = ConnectionManager.get();
        var statement = connection.prepareStatement(sql)) {
            statement.setLong(1, 2);
            var resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result.add(resultSet.getObject("id", Long.class));
            }
        }
        return result;
    }

    private static void checkMetaData() throws SQLException, InterruptedException {
        try (var connection = ConnectionManager.get()) {
            var catalogs = connection.getMetaData().getSchemas();
            while(catalogs.next()) {
                System.out.println(catalogs.getString(1));
            }
        }
    }

}
