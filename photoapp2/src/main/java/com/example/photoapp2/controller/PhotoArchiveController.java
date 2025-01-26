// PhotoArchiveController.java
package com.example.photoapp2.controller;

import com.example.photoapp2.service.PhotoArchiveService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
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
    public ResponseEntity<String> createNewTravelLog(@RequestParam("userId") String userId,
                                                     @RequestParam("travelDest") String travelDest,
                                                     @RequestParam("travelDate") String travelDate) {       
        try{
            photoArchiveService.createTravelLog(userId, travelDest, travelDate);
            return ResponseEntity.ok("Travel log uploaded successfully for userId: " + userId);
        }
        catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload travel log: " + e.getMessage());
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadPhotos(@RequestParam("travelId") String travelId,
                                               @RequestParam("files") MultipartFile[] files) {
        try {
            photoArchiveService.savePhotosAsArchive(travelId, files);
            return ResponseEntity.ok("Archive uploaded successfully for travelId: " + travelId);
        } catch (IOException | SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload archive: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{travelId}")
    public ResponseEntity<String> deleteArchive(@PathVariable String travelId) {
        try {
            photoArchiveService.deleteArchiveByTravelId(travelId);
            return ResponseEntity.ok("Archive deleted for travelId: " + travelId);
        } catch (SQLException | IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete archive: " + e.getMessage());
        }
    }

    @GetMapping("/download/{travelId}")
    public ResponseEntity<String> downloadArchive(@PathVariable String travelId) {
        try {
            String redirectUrl = photoArchiveService.downloadArchiveFromCloud(travelId);
            String message = "If your download didn't start automatically, please click the link: " + redirectUrl;
            
            return ResponseEntity.status(HttpStatus.FOUND)
                                 .header("Location", redirectUrl)
                                 .body(message);
        } catch (SQLException | IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to download archive: " + e.getMessage());
        }
    }
}
