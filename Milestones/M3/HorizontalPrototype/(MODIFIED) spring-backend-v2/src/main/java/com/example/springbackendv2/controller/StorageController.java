package com.example.springbackendv2.controller;

import java.io.IOException;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.example.springbackendv2.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    private AmazonS3 s3Client;

    @Value("${aws.s3.bucket}")
    private String bucketName;

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

    /* This endpoint loads the user's saved file directory
    *  The object keys (bucket paths) are returned as an array
    *  For example:
    *   ["testFolder/Yin_Wilson_HW3_Writeup.pdf","testFolder/test01.txt", "testFolder/subFolder/dot.txt"]*/
    @GetMapping("/getUserDir")
    public ArrayList<String> listBucket() {

        ListObjectsRequest listObjectsRequest = new ListObjectsRequest()
                .withBucketName(bucketName);
                // !!! not sure abt s3 structure yet so will uncomment when certain !!!
                // .withPrefix("{username}").withDelimiter("");

        ObjectListing objects = s3Client.listObjects(listObjectsRequest);

        List<S3ObjectSummary> summaries = objects.getObjectSummaries();
        ArrayList<String> obj_keys = new ArrayList<>();
        for (S3ObjectSummary objectSummary : summaries){
            obj_keys.add(objectSummary.getKey());
        }
        return obj_keys;
    }
}
