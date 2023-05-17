package com.example.springbackendv4.controller;

import com.example.springbackendv4.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

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

//    @Autowired
//    private AmazonS3 s3Client;
//
//    @Value("${aws.s3.bucket}")
//    private String bucketName;

    /* This function will handle file upload requests */
    @PostMapping("/upload/{userId}")
    public ResponseEntity<String> uploadFile(@PathVariable Long userId, @RequestParam(value = "file") MultipartFile file) {
        return new ResponseEntity<>(service.uploadFile(userId,file), HttpStatus.OK);
    }

    /* This function will handle file download requests */
    @GetMapping("/download/{fileName}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String fileName) {

//        ----------

        byte[] data = service.downloadFile(fileName);
        ByteArrayResource resource = new ByteArrayResource(data);

        return ResponseEntity
                .ok()
                .contentLength(data.length)
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition, ", "attachment; filename=\"" + fileName + "\"")
                .body(resource);
    }

    /* This function will handle file deletion requests */
    @DeleteMapping("/delete/{fileName}")
    public ResponseEntity<String> deleteFile(@PathVariable String fileName) {
        service.deleteFile(fileName);
        return new ResponseEntity<>("File deleted successfully", HttpStatus.OK);
    }

    @PostMapping("/restore/{fileName}")
    public ResponseEntity<String> restoreFile(@PathVariable String fileName) {
        service.restoreFile(fileName);
        return new ResponseEntity<>("File restored successfully", HttpStatus.OK);
    }

    @PostMapping("/moveFile/{source}/{destination}")
    public ResponseEntity<String> moveFile(@PathVariable String source, @PathVariable String destination) {
        service.moveFile(source,destination);
        return new ResponseEntity<>("File moved successfully", HttpStatus.OK);
    }

    @PostMapping("/moveFolder?source={source}&destination={destination}")
    public ResponseEntity<String> moveFolder(@RequestParam(value = "source") String source, @RequestParam(value = "destination") String destination){
        service.moveFolder(source,destination);
        return new ResponseEntity<>("Folder moved successfully", HttpStatus.OK);
    }

    @DeleteMapping("/trash")
    public ResponseEntity<String> emptyTrash(){
        service.emptyTrash();
        return new ResponseEntity<>("Trash emptied successfully", HttpStatus.OK);
    }

    @Scheduled(cron = "0 0 0 * * *")
    public ResponseEntity<String> automaticFileDeletion(){
        service.emptyTrash();
        return new ResponseEntity<>("Trash automatically emptied successfully", HttpStatus.OK);
    }

    /* This endpoint loads the user's saved file directory
     *  The object keys (bucket paths) are returned as an array*/
    @GetMapping("/getUserDir/{userName}")
    public ArrayList<String> listBucket(@RequestParam (value = "username") String userName) {
        return service.listDirectory(userName);
    }

    //endpoint for uploading file to specified path
    @PostMapping("/uploadFileToDest")
    public ResponseEntity<String> uploadToDest(@RequestParam(value = "file") MultipartFile file, @RequestParam(value = "path") String path) {
        return new ResponseEntity<>(service.uploadFileToDestination(file, path), HttpStatus.OK);
    }

    //endpoint for uploading folder to specified path
    @PostMapping("/uploadFolderToDest")
    public String uploadFolder(@RequestParam(value = "path") String path){
        return service.uploadFolderToDestination(path);
    }

    //endpoint for renaming a file
    @PutMapping("/renameFile")
    public String rename(@RequestParam(value = "key1") String fileKey, @RequestParam(value = "key2") String newFileKey) {
        service.moveFile(fileKey, newFileKey);
        return "Successful Rename: " + fileKey + " to " + newFileKey;
    }

    //endpoint for getting another user's folder/file
    @GetMapping("/viewDir")
    public ArrayList<String> viewObject(@RequestParam(value = "path") String path) {
        return service.listDirectory(path);
    }

    //endpoint for adding a file to a location in the user's directory
    @PostMapping("/addDirectory")
    public String addDirectory(@RequestParam(value = "src") String src, @RequestParam(value = "dest") String dest) {
        service.copyFile(src, dest);
        return "Copied from: " + src + " to " + dest;
    }


}
