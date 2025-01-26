// // PhotoArchiveDAO.java
// package com.example.photoapp2.dao;

// import com.example.photoapp2.model.PhotoArchive;

// import java.sql.*;

// import org.springframework.stereotype.Repository;

// @Repository
// public class PhotoArchiveDAO {

//     private static final String url = "jdbc:postgresql://localhost:5432/arena";
//     private static final String username = "admin";
//     private static final String password = "1234";

//     public void save(PhotoArchive archive) throws SQLException {
//         String sql = "INSERT INTO users.travel_archive (travel_id, archive_url) VALUES ((?::text)::uuid, ?)";
//         try (Connection connection = DriverManager.getConnection(url, username, password);
//              PreparedStatement statement = connection.prepareStatement(sql)) {
//             statement.setString(1, archive.getTravelId());
//             statement.setString(2, archive.getArchiveUrl());
//             statement.executeUpdate();
//         }
//     }

//     public PhotoArchive findByTravelId(String travelId) throws SQLException {
//         String sql = "SELECT id, travel_id, archive_url FROM users.travel_archive WHERE travel_id = (?::text)::uuid";
//         try (Connection connection = DriverManager.getConnection(url, username, password);
//              PreparedStatement statement = connection.prepareStatement(sql)) {
//             statement.setString(1, travelId);
//             try (ResultSet resultSet = statement.executeQuery()) {
//                 if (resultSet.next()) {
//                     PhotoArchive archive = new PhotoArchive();
//                     archive.setId(resultSet.getString("id"));
//                     archive.setTravelId(resultSet.getString("travel_id"));
//                     archive.setArchiveUrl(resultSet.getString("archive_url"));
//                     return archive;
//                 }
//             }
//         }
//         return null;
//     }

//     public void deleteByTravelId(String travelId) throws SQLException {
//         String sql = "DELETE FROM users.travel_archive WHERE travel_id = (?::text)::uuid";
//         try (Connection connection = DriverManager.getConnection(url, username, password);
//              PreparedStatement statement = connection.prepareStatement(sql)) {
//             statement.setString(1, travelId);
//             statement.executeUpdate();
//         }
//     }
// }
package com.example.photoapp2.dao;

import com.example.photoapp2.model.PhotoArchive;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.stereotype.Repository;

@Repository
public class PhotoArchiveDAO {

    private String url;
    private String username;
    private String password;

    private Properties sqlQueries;

    public PhotoArchiveDAO() {
        setUpConnection();  // Load database configuration

        // Load SQL queries
        sqlQueries = new Properties();
        try (InputStream input = new FileInputStream("src/main/resources/static/sql-queries.properties")) {
            sqlQueries.load(input);  // Load SQL queries from properties file
        } catch (IOException e) {
            System.err.println("Error loading SQL queries from properties file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void setUpConnection(){
        // Load database properties
        Properties dbProperties = new Properties();
        try (InputStream dbInput = new FileInputStream("src/main/resources/static/database.properties")) {
            dbProperties.load(dbInput);
            this.url = dbProperties.getProperty("db.url");
            this.username = dbProperties.getProperty("db.username");
            this.password = dbProperties.getProperty("db.password");
        } catch (IOException e) {
            System.err.println("Error loading database configuration: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public Boolean save(PhotoArchive archive) throws SQLException {
        setUpConnection();
        Boolean success = false;
        String sql = sqlQueries.getProperty("SQL_TO_INSERT_BY_TRAVEL_ID");
        try (Connection connection = DriverManager.getConnection(this.url, this.username, this.password);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, archive.getTravelId());
            statement.setString(2, archive.getArchiveUrl());
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    success = resultSet.getBoolean(1);
                }
            }
        }
        
        return success;
    }

    public PhotoArchive findByTravelId(String travelId) throws SQLException {
        setUpConnection();
        String sql = sqlQueries.getProperty("SQL_TO_SELECT_BY_TRAVEL_ID");
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, travelId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    PhotoArchive archive = new PhotoArchive();
                    archive.setId(resultSet.getString("id"));
                    archive.setTravelId(resultSet.getString("travel_id"));
                    archive.setArchiveUrl(resultSet.getString("archive_url"));
                    return archive;
                }
            }
        }
        return null;
    }

    public void deleteByTravelId(String travelId) throws SQLException {
        setUpConnection();
        String sql = sqlQueries.getProperty("SQL_TO_DELETE_BY_TRAVEL_ID");
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, travelId);
            statement.executeUpdate();
        }
    }

    public List<Map<String, Object>> getTravelLogsForUser(String userId) {
        setUpConnection();
        String sql = sqlQueries.getProperty("SQL_TO_FIND_ALL_BY_USER_ID");
        List<Map<String, Object>> travels = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, userId);

            try (ResultSet rs = statement.executeQuery()) {
                // If we have a result, process it
                if (rs.next()) {
                    String jsonbData = rs.getString(1); 

                    // Use Jackson to parse the jsonb array into a List<Map<String, Object>>
                    ObjectMapper objectMapper = new ObjectMapper();
                    try {
                        travels = objectMapper.readValue(jsonbData, new TypeReference<List<Map<String, Object>>>() {});
                    } catch (JsonProcessingException e) {
                        System.err.println("Error processing JSON: " + e.getMessage());
                        e.printStackTrace();
                    }
                }
            }
        } catch (SQLException e) {
            // Log SQLException and handle it
            System.err.println("SQL error: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            // Catch any unexpected exceptions
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }

        return travels;
    }

    public String createTravelLog(String userId, String travelDest, String travelDate) {
        setUpConnection();
        String sql = sqlQueries.getProperty("SQL_TO_CREATE_TRAVEL_BY_USER_ID");
        String travelId = null;

        try (Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement(sql)) {

            // Set the parameters for the insert query
            statement.setString(1, userId); // Set the user_id
            statement.setString(2, travelDest); // Set a default or user-provided travel name
            statement.setString(3, travelDate); // Set current date as default
            
            try (ResultSet rs = statement.executeQuery()) {
                // If we have a result, process it
                if (rs.next()) {
                    travelId = rs.getString(1);
                    if (travelId != null) {
                        System.out.println("Travel log successfully created for user: " + userId);
                        return travelId;
                    } else {
                        System.out.println("Failed to create travel log for user: " + userId);
                    }
                } else {
                    throw new SQLException("No result returned from the function.");
                }
            }
            
        } catch (SQLException e) {
            // Handle potential SQL exceptions
            System.err.println("SQL error occurred while creating travel log: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            // Handle any other exceptions
            System.err.println("Unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
        return travelId;
    }

    public void deleteTravelLogByTravelId(String travelId) throws SQLException {
        setUpConnection();
        String sql = sqlQueries.getProperty("SQL_TO_DELETE_TRAVEL_LOG_BY_TRAVEL_ID");
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, travelId);
            statement.executeUpdate();
        }
    }
}
