package com.example.springbackendv2.service.impl;

import com.amazonaws.services.elasticfilesystem.AmazonElasticFileSystem;
import com.example.springbackendv2.service.FileService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


public class FileServiceImpl implements FileService{
    private AmazonElasticFileSystem efsClient;
    private String fileSystemId;
    private String mountPath;

    public FileServiceImpl(AmazonElasticFileSystem efsClient, String fileSystemId, String mountPath) {
        this.efsClient = efsClient;
        this.fileSystemId = fileSystemId;
        this.mountPath = mountPath;
    }

    @Override
    public void storeFile(File file) throws IOException {
        Path path = Paths.get(mountPath, file.getName());
        Files.copy(file.toPath(), path, StandardCopyOption.REPLACE_EXISTING);
    }
}
