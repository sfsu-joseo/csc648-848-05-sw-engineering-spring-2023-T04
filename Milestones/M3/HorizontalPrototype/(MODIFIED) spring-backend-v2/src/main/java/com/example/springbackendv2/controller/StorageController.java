package com.example.springbackendv2.controller;

import java.io.IOException;
import com.example.springbackendv2.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.NoSuchFileException;

/*
* This class will handle requests for file
* upload, download, and deletion from the
* front end. Restrictions should be implemented
* later, so that only signed-in users can
* interact with the aws s3 bucket(s).
*/

@RestController
@RequestMapping("/file")
public class StorageController {

    @Autowired
    private StorageService service;

    /* This function will handle file upload requests */
    @PostMapping("/upload")
    @PreAuthorize("isSignedIn()")
    public ResponseEntity<String> uploadFile(@RequestParam(value = "file") MultipartFile file) {
        return new ResponseEntity<>(service.uploadFile(file), HttpStatus.OK);
    }

    /* This function will handle file download requests */
    @GetMapping("/download/{fileName}")
    @PreAuthorize("isSignedIn()")
    public ResponseEntity<?> downloadFile(@PathVariable String fileName) {
        try {
            byte[] data = service.downloadFile(fileName);
            ByteArrayResource resource = new ByteArrayResource(data);

            return ResponseEntity
                    .ok()
                    .contentLength(data.length)
                    .header("Content-type", "application/octet-stream")
                    .header("Content-disposition, ", "attachment; filename=\"" + fileName + "\"")
                    .body(resource);
        } catch (NoSuchFileException ex) {
            return new ResponseEntity<>("File not found", HttpStatus.NOT_FOUND);
        } catch (IOException ex) {
            return new ResponseEntity<>("Failed to download file", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /* This function will handle file deletion requests */
    @DeleteMapping("/delete/{fileName}")
    @PreAuthorize("isSignedIn()")
    public ResponseEntity<?> deleteFile(@PathVariable String fileName) {
        try {
            service.deleteFile(fileName);
            return new ResponseEntity<>("File deleted successfully", HttpStatus.OK);
        } catch (NoSuchFileException ex) {
            return new ResponseEntity<>("File not found", HttpStatus.NOT_FOUND);
        } catch (IOException ex) {
            return new ResponseEntity<>("Failed to delete file", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
