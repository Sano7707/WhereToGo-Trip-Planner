// PhotoArchiveController.java
package com.example.photoapp2.controller;

import com.example.photoapp2.service.PhotoArchiveService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/photos")
public class PhotoArchiveController {

    private final PhotoArchiveService photoArchiveService;

    public PhotoArchiveController(PhotoArchiveService photoArchiveService) {
        this.photoArchiveService = photoArchiveService;
    }

    @GetMapping("/travels")
    @CrossOrigin
    public ResponseEntity<Object> getAllTravelIdsForUser(@RequestParam("userId") String userId) {
        try {
            // Call the service method to retrieve the travelIds
            List<Map<String, Object>> travelIds = photoArchiveService.getTravelLogsForUser(userId);

            if (travelIds.isEmpty()) {
                return ResponseEntity.status(404).body("No travel logs found for the provided userId.");
            }

            // Return the travelIds as a JSON array
            return ResponseEntity.ok(travelIds);
            
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body("Failed to fetch travel logs: " + e.getMessage());
        }
    }

    @PostMapping("/create")
    @CrossOrigin
    public ResponseEntity<Map<String, String>> createNewTravelLog(@RequestParam("userId") String userId,
                                                     @RequestParam("travelDest") String travelDest,
                                                     @RequestParam("travelDate") String travelDate) {       
        try{
            String travelId = photoArchiveService.createTravelLog(userId, travelDest, travelDate);
            // Return structured JSON response
            Map<String, String> response = new HashMap<>();
            response.put("message", "Travel log uploaded successfully");
            response.put("userId", userId);
            response.put("travelId", travelId);

            return ResponseEntity.ok(response);
        } catch (SQLException e) {
            // Error response
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to upload travel log");
            errorResponse.put("details", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PostMapping("/upload/{travelId}")
    @CrossOrigin
    public ResponseEntity<String> uploadPhotos(@PathVariable("travelId") String travelId,
                                               @RequestParam("files") MultipartFile[] files) {
        try {
            photoArchiveService.savePhotosAsArchive(travelId, files);
            return ResponseEntity.ok("Archive uploaded successfully for travelId: " + travelId);
        } catch (IOException | SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload archive: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete-files/{travelId}")
    @CrossOrigin
    public ResponseEntity<String> deleteArchive(@PathVariable String travelId) {
        try {
            photoArchiveService.deleteArchiveByTravelId(travelId);
            return ResponseEntity.ok("Archive deleted for travelId: " + travelId);
        } catch (SQLException | IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete archive: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{travelId}")
    @CrossOrigin
    public ResponseEntity<String> deleteTravelLog(@PathVariable String travelId) {
        try {
            photoArchiveService.deleteTravelLogByTravelId(travelId);
            return ResponseEntity.ok("Travel deleted for travelId: " + travelId);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete travel: " + e.getMessage());
        }
    }

    @GetMapping("/download/{travelId}")
    @CrossOrigin
    public ResponseEntity<Object> downloadArchive(@PathVariable String travelId) {
        try {
            // Fetch the ZIP file data as a byte array
            byte[] data = photoArchiveService.downloadArchiveFromCloud(travelId);

            // Set headers to indicate a file download
            return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM) // For binary file downloads
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"archive.zip\"") // Suggest a file name
                .body(data);

        } catch (SQLException | IOException e) {
            // Return an error response if something goes wrong
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(("Failed to download archive: " + e.getMessage()).getBytes());
        }
    }
}
