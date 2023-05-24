package com.example.springbackendv4.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Objects;

@Service
public class StorageService {

    @Value("${application.bucket1.name1}")
    private String fileBucket;

    @Value("${application.bucket2.name2}")
    private String JsonBucket;

    @Autowired
    private AmazonS3 s3Client1;

    @Autowired
    private AmazonS3 s3Client2;


    public String uploadFile(MultipartFile file, String key) {
        File fileObject = convertMultiPartFileToFile(file);
        s3Client1.putObject(new PutObjectRequest(fileBucket, key, fileObject));
        fileObject.delete();

        return "File uploaded: " + file.getOriginalFilename() + " as " + key;
    }

    public void streamUploadFile(InputStream inputStream, String key) {
        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(inputStream.available()); // Set the content length explicitly
            s3Client1.putObject(new PutObjectRequest(fileBucket, key, inputStream, metadata));
        } catch (IOException e) {
            // Handle the exception appropriately
            throw new RuntimeException("Failed to read the input stream: " + e.getMessage(), e);
        } catch (AmazonS3Exception e) {
            // Handle the exception appropriately
            throw new RuntimeException("Failed to upload the file to S3: " + e.getMessage(), e);
        }
    }

    public String uploadJson(MultipartFile file, String key) {
        File fileObject = convertMultiPartFileToFile(file);
        s3Client2.putObject(new PutObjectRequest(JsonBucket, key, fileObject));
        fileObject.delete();

        return "File uploaded: " + file.getOriginalFilename() + " as " + key;
    }

    public byte[] downloadFile(String fileName) {
        S3Object s3Object = s3Client1.getObject(fileBucket, fileName);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();

        try {
            byte[] content = IOUtils.toByteArray(inputStream);
            return content;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] downloadJson(String fileName) {
        S3Object s3Object = s3Client2.getObject(JsonBucket, fileName);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();

        try {
            byte[] content = IOUtils.toByteArray(inputStream);
            return content;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String deleteFile(String fileName) {
        s3Client1.deleteObject(fileBucket, fileName);
        return fileName + " removed";
    }

    public String deleteJson(String fileName) {
        s3Client2.deleteObject(JsonBucket, fileName);
        return fileName + " removed";
    }

    public ResponseEntity<Object> streamFile(String fileName, HttpServletRequest request, HttpServletResponse response) {
        try {
            S3Object s3Object = s3Client1.getObject(fileBucket, fileName);
            S3ObjectInputStream inputStream = s3Object.getObjectContent();

            String contentType = getContentType(fileName);
            response.setHeader(HttpHeaders.CONTENT_TYPE, contentType);

            long contentLength = s3Object.getObjectMetadata().getContentLength();
            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];

            String rangeHeader = request.getHeader(HttpHeaders.RANGE);
            if (rangeHeader != null) {
                String[] rangeValues = rangeHeader.replace("bytes=", "").split("-");
                long startRange = Long.parseLong(rangeValues[0]);
                long endRange = contentLength - 1;

                if (rangeValues.length == 2) {
                    endRange = Long.parseLong(rangeValues[1]);
                }

                long requestedBytes = endRange - startRange + 1;

                response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
                response.setHeader(HttpHeaders.CONTENT_RANGE, "bytes " + startRange + "-" + endRange + "/" + contentLength);
                response.setHeader(HttpHeaders.ACCEPT_RANGES, "bytes");
                response.setContentLengthLong(requestedBytes);

                inputStream.skip(startRange);
            } else {
                response.setStatus(HttpServletResponse.SC_OK);
                response.setHeader(HttpHeaders.ACCEPT_RANGES, "bytes");
                response.setHeader(HttpHeaders.CONTENT_LENGTH, String.valueOf(contentLength));
            }

            OutputStream outputStream = response.getOutputStream();
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            outputStream.flush();
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }


    private String getContentType(String fileName) {
        // Get the file extension
        String fileExtension = FilenameUtils.getExtension(fileName);

        // Determine the Content-Type based on the file extension
        return switch (fileExtension.toLowerCase()) {
            case "mp4" -> "video/mp4";
            case "mov" -> "video/quicktime";
            case "avi" -> "video/x-msvideo";
            // Add more cases for other supported video file types
            default -> "application/octet-stream"; // Default MIME type if the file extension is not recognized
        };
    }

    private File convertMultiPartFileToFile(MultipartFile file) {
        File convertedFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e); // add exception logging for this service later
        }

        return convertedFile;
    }


// ------------------------------------ CURRENTLY NOT USED ------------------------------------------- //


    //    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private TrashMasterRepository trashMasterRepository;

//    private static final String trashFolder = "trash/";

//    public String uploadFile(Long userId, MultipartFile file) {
//        File fileObject = convertMultiPartFileToFile(file);
//        Integer fileSize = Math.toIntExact(fileObject.length());
//        Integer currentUserStorage = userRepository.getStorageLeftByUserId(userId);
//        if(currentUserStorage >= fileSize) {
//            //this filename is adjusted to ensure uniqueness
//            String filename = System.currentTimeMillis() + "-" + file.getOriginalFilename();
//            s3Client.putObject(new PutObjectRequest(bucketName, filename, fileObject));
//            fileObject.delete();
//            userRepository.updateUserStorageByUserId(currentUserStorage - fileSize, userId); // Updated user storage after upload.
//            return "File uploaded: " + filename;
//        } else {
//            throw new NotEnoughStorageException();
//        }
//    }


//    public void deleteFile(String fileName) {
//        //designating a path of the targeted file
//        String trashedFile = trashFolder+fileName;
//
//        //creating a copy of the original file and placing in the trash folder
//        CopyObjectRequest copyObjectRequest = new CopyObjectRequest(bucketName, fileName, bucketName, trashedFile);
//        s3Client.copyObject(copyObjectRequest);
//
//        //Save info to trashmaster
//        TrashMaster newTrashMaster = new TrashMaster();
//        newTrashMaster.setOriginalPath(fileName);
//        newTrashMaster.setTrashFolderPath(trashedFile);
//        trashMasterRepository.save(newTrashMaster);
//
//        //deleting the original file
//        s3Client.deleteObject(bucketName, fileName);
//        //return fileName + " removed";
//    }
//
//    public void restoreFile(String trashFolderPath) {
//        //fetching original path of trash file
//        String originalFilePath = trashMasterRepository.findOriginalFilePathByTrashPath(trashFolderPath);
//
//        //creating a copy of the trash file and placing it in original Place
//        CopyObjectRequest copyObjectRequest = new CopyObjectRequest(bucketName, trashFolderPath, bucketName, originalFilePath);
//        s3Client.copyObject(copyObjectRequest);
//
//        //Delete info from trashMaster
//        trashMasterRepository.deleteByTrashFilePath(trashFolderPath);
//
//        //deleting the original file
//        s3Client.deleteObject(bucketName, trashFolderPath);
//    }
//
//    public void emptyTrash(){
//        //TODO: add logic to decrease storage size after deleting file from trash when implementing delete from trash.
//        //Making a list of all the files in the trash bin
//        ObjectListing listings = s3Client.listObjects(bucketName, trashFolder);
//        List<S3ObjectSummary> summaries = listings.getObjectSummaries();
//
//        //Selecting each of the files that are to be purged
//        List<String> deletedFiles = new ArrayList<>();
//        for (S3ObjectSummary summary : summaries) {
//            deletedFiles.add(summary.getKey());
//        }
//
//        //Deleting all files that have been selected
//        if(!deletedFiles.isEmpty()){
//            DeleteObjectsRequest deleteObjectsRequest = new DeleteObjectsRequest(bucketName).withKeys(deletedFiles.toArray(new String[0]));
//            s3Client.deleteObjects(deleteObjectsRequest);
//        }
//
//        trashMasterRepository.deleteAll();
//    }

//    public ArrayList<String> getRoot(String username) {
//        ListObjectsRequest listObjectsRequest = new ListObjectsRequest()
//                .withBucketName(bucketName)
//                .withPrefix(username).withDelimiter("");
//
//        ObjectListing objects = s3Client.listObjects(listObjectsRequest);
//
//        List<S3ObjectSummary> summaries = objects.getObjectSummaries();
//        ArrayList<String> obj_keys = new ArrayList<>();
//        for (S3ObjectSummary objectSummary : summaries){
//            obj_keys.add(objectSummary.getKey());
//        }
//        return obj_keys;
//    }
//
//    public void moveFolder(String oldLocation, String newLocation){
//        ObjectListing listings = s3Client.listObjects(bucketName, oldLocation);
//        List<S3ObjectSummary> summaries = listings.getObjectSummaries();
//
//        for (S3ObjectSummary summary : summaries) {
//            String key = summary.getKey();
//            String newKey = key.replace(oldLocation, newLocation);
//
//            s3Client.copyObject(bucketName, key, bucketName, newKey);
//            s3Client.deleteObject(bucketName, key);
//        }
//
//    }

    //places the file at the specified path
//    public String uploadFileToDestination(MultipartFile file, String filePath)
//    {
//        //convert multipart file to file
//        File fileObject = convertMultiPartFileToFile(file);
//
//        //put object in specified path
//        s3Client.putObject(new PutObjectRequest(bucketName, filePath, fileObject));
//        fileObject.delete();
//        return("File uploaded to " + filePath);
//    }
//
//    //uploads empty folder to specified path
//    public String uploadFolderToDestination(String path){
//        s3Client.putObject(new PutObjectRequest(bucketName, path, "/"));
//        return "Folder created at " + path;
//    }
//
//    //move a file and delete
//    //used for delete
//    public void moveFile(String fileKey, String newFileKey) {
//        //make a copy of the file with a new name
//        s3Client.copyObject(bucketName, fileKey, bucketName, newFileKey);
//        //delete old file
//        s3Client.deleteObject(bucketName, fileKey);
//    }
//
//    //lists files and folders in a directory
//    //used for loading root dir and to view other dirs
//    public ArrayList<String> listDirectory(String path) {
//
//        ListObjectsRequest listObjectsRequest = new ListObjectsRequest()
//                .withBucketName(bucketName)
//                .withPrefix(path).withDelimiter("");
//
//        ObjectListing objects = s3Client.listObjects(listObjectsRequest);
//
//        List<S3ObjectSummary> summaries = objects.getObjectSummaries();
//        ArrayList<String> obj_keys = new ArrayList<>();
//
//        for (S3ObjectSummary objectSummary : summaries){
//            obj_keys.add(objectSummary.getKey());
//        }
//        return obj_keys;
//    }
//
//    //copy a file
//    public void copyFile(String fileKey, String newFileKey) {
//        //make a copy of the file with a new name
//        s3Client.copyObject(bucketName, fileKey, bucketName, newFileKey);
//    }
}
