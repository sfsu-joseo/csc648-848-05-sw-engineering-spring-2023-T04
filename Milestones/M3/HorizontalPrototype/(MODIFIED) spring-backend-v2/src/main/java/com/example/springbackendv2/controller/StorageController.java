package com.example.springbackendv2.controller;

import java.io.IOException;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController

@RequestMapping("/file")
public class StorageController {

    private AmazonS3 s3Client;
    
    @Value("{cloud.aws.credentials.access-key}")
    private String accessKey;
    
    @Value("${application.bucket.name}")
    private String bucketName;

    @PostMapping("/upload")
    String uploadFile(@RequestParam(value = "file") MultipartFile file) throws IOException {
        s3Client.putObject(new PutObjectRequest(bucketName, file.getOriginalFilename(), file.getInputStream(), null));
        return new ResponseEntity<>("File uploaded successfully", HttpStatus.OK);
    }

    @GetMapping("/download/{fileName}")
    ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String fileName) throws IOException {
        S3Object object = s3Client.getObject(new GetObjectRequest(bucketName, fileName));
        byte[] data = IOUtils.toByteArray(object.getObjectContent());
        ByteArrayResource resource = new ByteArrayResource(data);

        return ResponseEntity
                .ok()
                .contentLength(data.length)
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition, ", "attachment; filename=\"" + fileName + "\"")
                .body(resource);
    }

    @DeleteMapping("/delete/{fileName}")
    String deleteFile(@PathVariable String fileName) {
        s3Client.deleteObject(new DeleteObjectRequest(bucketName, fileName));
        return new ResponseEntity<>("File deleted successfully", HttpStatus.OK);
    }
}
