package com.example.photoapp2.service;

import com.example.photoapp2.dao.PhotoArchiveDAO;
import com.example.photoapp2.model.PhotoArchive;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class PhotoArchiveService {

    private final PhotoArchiveDAO photoArchiveDAO;
    private Storage storage = StorageOptions.getDefaultInstance().getService();
    private static final String bucketName = "travel-photos-archive-2025";
    private static String CLOUD_STORAGE_BASE_URL = "https://storage.cloud.google.com";

    public PhotoArchiveService(PhotoArchiveDAO photoArchiveDAO){
        this.photoArchiveDAO = photoArchiveDAO;
    }

    private void initializeStorage() throws IOException {
        StorageOptions storageOptions = StorageOptions.newBuilder()
                .setProjectId("pelagic-sorter-448915-h0")
                .setCredentials(GoogleCredentials.fromStream(new FileInputStream("src/main/resources/static/pelagic-sorter-448915-h0-fa3ca77a5f13.json"))).build();
        storage = storageOptions.getService();
    }

    public void savePhotosAsArchive(String travelId, MultipartFile[] files) throws IOException, SQLException {
        initializeStorage();
        Boolean isArchiveURLCreated = false;

        // Create a ZIP archive
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ZipOutputStream zos = new ZipOutputStream(baos)) {
            for (MultipartFile file : files) {
                ZipEntry entry = new ZipEntry(file.getOriginalFilename());
                zos.putNextEntry(entry);
                zos.write(file.getBytes());
                zos.closeEntry();
            }
        }

        byte[] archiveData = baos.toByteArray();
        String archiveName = "archive_" + travelId + ".zip";
        String objectName = "archives/" + UUID.randomUUID() + "/" + archiveName;

        // Upload archive to Google Cloud Storage
        BlobInfo blobInfo = BlobInfo.newBuilder(bucketName, objectName).setContentType("application/zip").build();

        com.google.cloud.storage.Blob blob = null;

        if (archiveData != null) 
        {
            blob = storage.create(blobInfo, archiveData);
        }
        
        if (blob != null && blob.exists()) {
            // Save archive URL to PostgreSQL
            String archiveUrl = String.format("%s/%s/%s", CLOUD_STORAGE_BASE_URL, bucketName, objectName);
            PhotoArchive archive = new PhotoArchive();
            archive.setTravelId(travelId);
            archive.setArchiveUrl(archiveUrl);

            isArchiveURLCreated = photoArchiveDAO.save(archive);
            }
        else {
            System.out.println("Upload failed!");
        }

        if( isArchiveURLCreated == true) {
            System.out.println("Archive uploaded successfully for travelId: " + travelId);
        }
        else {
            storage.delete(bucketName, objectName);
            System.out.println("Failed to upload archive for travelId: " + travelId);
        }
    }

    public PhotoArchive getArchiveByTravelId(String travelId) throws SQLException {
        return photoArchiveDAO.findByTravelId(travelId);
    }

    public void deleteArchiveByTravelId(String travelId) throws SQLException, IOException {
        initializeStorage();

        PhotoArchive archive = photoArchiveDAO.findByTravelId(travelId);

        if (archive != null) {
            // Delete the archive from Google Cloud Storage
            String objectName = archive.getArchiveUrl().replace(
                    String.format("%s/%s/", CLOUD_STORAGE_BASE_URL, bucketName), "");
            storage.delete(bucketName, objectName);
        }

        // Delete the archive record from PostgreSQL
        photoArchiveDAO.deleteByTravelId(travelId);
    }

    public String downloadArchiveFromCloud(String travelId) throws IOException, SQLException {
        PhotoArchive archive = photoArchiveDAO.findByTravelId(travelId);
        
        if (archive == null) {
            throw new IOException("Archive not found for travelId: " + travelId);
        }
        
        return archive.getArchiveUrl();
    }

    public void createTravelLog(String userId, String travelDest, String travelDate) throws SQLException {
        photoArchiveDAO.createTravelLog(userId, travelDest, travelDate);
    }

    public java.util.List<Map<String, Object>> getTravelLogsForUser(String userId) {
        return photoArchiveDAO.getTravelLogsForUser(userId);
    }
    
    
}
