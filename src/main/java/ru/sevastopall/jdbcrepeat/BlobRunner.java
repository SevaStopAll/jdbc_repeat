package ru.sevastopall.jdbcrepeat;

import ru.sevastopall.jdbcrepeat.util.ConnectionManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.sql.Blob;
import java.sql.SQLException;

public class BlobRunner {
    public static void main(String[] args) throws SQLException, IOException, InterruptedException {
        getImage();

    }

    private static void getImage() throws SQLException, IOException, InterruptedException {
        var sql = """
                SELECT image 
                FROM aircraft
                WHERE id = ?
                """;
        try (var connection = ConnectionManager.get();
        var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, 1);
            var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                var bytes = resultSet.getBytes("image");
                Files.write(Path.of("resources", "boeing777_new.jpg"), bytes, StandardOpenOption.CREATE);
            }
        }
    }

    private static void saveImage() throws SQLException, IOException, InterruptedException {
        var sql = """
                UPDATE aircraft 
                SET image = ?
                WHERE id = 1
                """;
        try (var connection = ConnectionManager.get();
        var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setBytes(1, Files.readAllBytes(Path.of("resources", "boeing777.jpg")));
            preparedStatement.executeUpdate();
        }
    }
}
