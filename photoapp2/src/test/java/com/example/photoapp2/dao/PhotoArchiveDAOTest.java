package com.example.photoapp2.dao;

import com.example.photoapp2.model.PhotoArchive;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyString;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;


class PhotoArchiveDAOTest {

    // @Mock
    // private Connection mockConnection;

    // @Mock
    // private PreparedStatement mockPreparedStatement;

    // @Mock
    // private ResultSet mockResultSet;

    // private PhotoArchiveDAO photoArchiveDAO;

    // private final String url = "jdbc:postgresql://localhost:5432/arena";
    // private final String username = "admin";
    // private final String password = "1234";

    // private Storage storage = StorageOptions.getDefaultInstance().getService();

    // @BeforeEach
    // void setUp() throws SQLException {
    //     Mockito.clearAllCaches(); // Add this line to deregister existing static mock registration

    //     mockConnection = Mockito.mock(Connection.class);
    //     mockPreparedStatement = Mockito.mock(PreparedStatement.class);
    //     mockResultSet = Mockito.mock(ResultSet.class);

    //     Mockito.mockStatic(DriverManager.class);
    //     when(DriverManager.getConnection(anyString(), anyString(), anyString())).thenReturn(mockConnection);

    //     when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
    //     when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
    // }

    // @Test
    // void testSavePhotoArchive() throws SQLException, IOException {

    //     String travelId = "123e4567-e89b-12d3-a456-426614174101";
    //     String bucketName = "travel-photos-archive-2025";

    //     // Validate input file path
    //     Path filePathObj = Path.of("C:\\Users\\jjuul\\Downloads\\Protocol_Comparison_Table.jpg");
    //     if (!Files.exists(filePathObj)) {
    //         throw new IllegalArgumentException("File does not exist at path: " + filePathObj.toString());
    //     }

    //     StorageOptions storageOptions = StorageOptions.newBuilder()
    //             .setProjectId("pelagic-sorter-448915-h0")
    //             .setCredentials(GoogleCredentials.fromStream(new FileInputStream("C:/Users/jjuul/javaprojects/photoapp2/src/main/resources/static/pelagic-sorter-448915-h0-fa3ca77a5f13.json"))).build();

    //     storage = storageOptions.getService();

    //     // Read file data into byte array
    //     byte[] archiveData = Files.readAllBytes(filePathObj);

    //     // Generate archive name and object name
    //     String archiveName = "archive_" + travelId + ".zip";
    //     String objectName = "archives/" + UUID.randomUUID() + "/" + archiveName;

    //     // Create BlobInfo object for uploading the file
    //     BlobInfo blobInfo = BlobInfo.newBuilder(bucketName, objectName) 
    //                                 .setContentType("image/jpeg")
    //                                 .build();

    //     com.google.cloud.storage.Blob blob = storage.create(blobInfo, archiveData);
        
    //     if (blob != null && blob.exists()) {
    //         System.out.println("Upload successful! Blob name: " + blob.getName());
    //         // Save archive URL to PostgreSQL
    //         String archiveUrl = String.format("https://storage.googleapis.com/%s/%s", bucketName, objectName);

    //         mockPreparedStatement = mockConnection.prepareStatement("INSERT INTO travel_archive (travel_id, archive_url) VALUES ((?::text)::uuid, ?)");
    //         mockPreparedStatement.setString(1,"123e4567-e89b-12d3-a456-426614174001");
    //         mockPreparedStatement.setString(2, archiveUrl);
    //         mockPreparedStatement.executeUpdate();
    //     } else {
    //         System.out.println("Upload failed!");
    //     }


    // }

}
