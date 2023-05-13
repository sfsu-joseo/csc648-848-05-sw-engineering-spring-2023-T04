package com.example.springbackendv2.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import com.example.springbackendv2.config.StorageConfig;
import com.example.springbackendv2.exception.NotEnoughStorageException;
import com.example.springbackendv2.model.TrashMaster;
import com.example.springbackendv2.repository.TrashMasterRepository;
import com.example.springbackendv2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class StorageService {

//    @Value("${aws.s3.bucket}")
//    private String bucketName;
//
//    @Autowired
//    private StorageConfig s3Client;


    @Value("${application.bucket.name}")
    private String bucketName;

    @Autowired
    private AmazonS3 s3Client;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TrashMasterRepository trashMasterRepository;

    private static final String trashFolder = "trash/";

    public String uploadFile(Long userId, MultipartFile file) {
        File fileObject = convertMultiPartFileToFile(file);
        Integer fileSize = Math.toIntExact(fileObject.length());
        Integer currentUserStorage = userRepository.getStorageLeftByUserId(userId);
        if(currentUserStorage >= fileSize) {
            //this filename is adjusted to ensure uniqueness
            String filename = System.currentTimeMillis() + "-" + file.getOriginalFilename();
            s3Client.putObject(new PutObjectRequest(bucketName, filename, fileObject));
            fileObject.delete();
            userRepository.updateUserStorageByUserId(currentUserStorage - fileSize, userId); // Updated user storage after upload.
            return "File uploaded: " + filename;
        } else {
            throw new NotEnoughStorageException();
        }
    }

    public byte[] downloadFile(String fileName) {
        S3Object s3Object = s3Client.getObject(bucketName, fileName);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();

        try {
            byte[] content = IOUtils.toByteArray(inputStream);
            return content;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteFile(String fileName) {
        //designating a path of the targeted file
        String trashedFile = trashFolder+fileName;

        //creating a copy of the original file and placing in the trash folder
        CopyObjectRequest copyObjectRequest = new CopyObjectRequest(bucketName, fileName, bucketName, trashedFile);
        s3Client.copyObject(copyObjectRequest);

        //Save info to trashmaster
        TrashMaster newTrashMaster = new TrashMaster();
        newTrashMaster.setOriginalPath(fileName);
        newTrashMaster.setTrashFolderPath(trashedFile);
        trashMasterRepository.save(newTrashMaster);

        //deleting the original file
        s3Client.deleteObject(bucketName, fileName);
        //return fileName + " removed";
    }

    public void restoreFile(String trashFolderPath) {
        //fetching original path of trash file
        String originalFilePath = trashMasterRepository.findOriginalFilePathByTrashPath(trashFolderPath);

        //creating a copy of the trash file and placing it in original Place
        CopyObjectRequest copyObjectRequest = new CopyObjectRequest(bucketName, trashFolderPath, bucketName, originalFilePath);
        s3Client.copyObject(copyObjectRequest);

        //Delete info from trashMaster
        trashMasterRepository.deleteByTrashFilePath(trashFolderPath);

        //deleting the original file
        s3Client.deleteObject(bucketName, trashFolderPath);
    }

    public void emptyTrash(){
        //TODO: add logic to decrease storage size after deleting file from trash when implementing delete from trash.
        //Making a list of all the files in the trash bin
        ObjectListing listings = s3Client.listObjects(bucketName, trashFolder);
        List<S3ObjectSummary> summaries = listings.getObjectSummaries();

        //Selecting each of the files that are to be purged
        List<String> deletedFiles = new ArrayList<>();
        for (S3ObjectSummary summary : summaries) {
            deletedFiles.add(summary.getKey());
        }

        //Deleting all files that have been selected
        if(!deletedFiles.isEmpty()){
            DeleteObjectsRequest deleteObjectsRequest = new DeleteObjectsRequest(bucketName).withKeys(deletedFiles.toArray(new String[0]));
            s3Client.deleteObjects(deleteObjectsRequest);
        }

        trashMasterRepository.deleteAll();
    }

//    This will get the root folder of the user... I think.

    public ArrayList<String> getRoot(String username) {
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest()
                .withBucketName(bucketName)
                .withPrefix(username).withDelimiter("");

        ObjectListing objects = s3Client.listObjects(listObjectsRequest);

        List<S3ObjectSummary> summaries = objects.getObjectSummaries();
        ArrayList<String> obj_keys = new ArrayList<>();
        for (S3ObjectSummary objectSummary : summaries){
            obj_keys.add(objectSummary.getKey());
        }
        return obj_keys;
    }

    public void moveFile(String oldLocation, String newLocation) {
        //creating a copy of the original file and placing in the trash folder
        CopyObjectRequest copyObjectRequest = new CopyObjectRequest(bucketName, oldLocation, bucketName, newLocation);
        s3Client.copyObject(copyObjectRequest);

        //deleting the original file
        s3Client.deleteObject(bucketName, oldLocation);
        //return fileName + " removed";
    }

    public void moveFolder(String oldLocation, String newLocation){
        ObjectListing listings = s3Client.listObjects(bucketName, oldLocation);
        List<S3ObjectSummary> summaries = listings.getObjectSummaries();

        for (S3ObjectSummary summary : summaries) {
            String key = summary.getKey();
            String newKey = key.replace(oldLocation, newLocation);

            s3Client.copyObject(bucketName, key, bucketName, newKey);
            s3Client.deleteObject(bucketName, key);
        }

    }

    private File convertMultiPartFileToFile(MultipartFile file) {
        File convertedFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e); // add exception logging for this service later
        }

        return convertedFile;
    }

    //places the file at the specified path
    public String uploadFileToDestination(MultipartFile file, String filePath)
    {
        //convert multipart file to file
        File fileObject = convertMultiPartFileToFile(file);

        //put object in specified path
        s3Client.putObject(new PutObjectRequest(bucketName, filePath, fileObject));
        fileObject.delete();
        return("File uploaded to " + filePath);
    }

    //uploads empty folder to specified path
    public String uploadFolderToDestination(String path){
        s3Client.putObject(new PutObjectRequest(bucketName, path, "/"));
        return "Folder created at " + path;
    }

    //move a file and delete
    //used for delete
    public void moveFile(String fileKey, String newFileKey) {
        //make a copy of the file with a new name
        s3Client.copyObject(bucketName, fileKey, bucketName, newFileKey);
        //delete old file
        s3Client.deleteObject(bucketName, fileKey);
    }

    //lists files and folders in a directory
    //used for loading root dir and to view other dirs
    public ArrayList<String> listDirectory(String path) {

        ListObjectsRequest listObjectsRequest = new ListObjectsRequest()
                .withBucketName(bucketName)
                .withPrefix(path).withDelimiter("");

        ObjectListing objects = s3Client.listObjects(listObjectsRequest);

        List<S3ObjectSummary> summaries = objects.getObjectSummaries();
        ArrayList<String> obj_keys = new ArrayList<>();

        for (S3ObjectSummary objectSummary : summaries){
            obj_keys.add(objectSummary.getKey());
        }
        return obj_keys;
    }

    //copy a file
    public void copyFile(String fileKey, String newFileKey) {
        //make a copy of the file with a new name
        s3Client.copyObject(bucketName, fileKey, bucketName, newFileKey);
    }
}
