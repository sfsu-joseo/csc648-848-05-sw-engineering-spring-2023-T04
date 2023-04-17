package com.example.springbackendv2.service;



import java.io.File;
import java.io.IOException;

public interface FileService {
    void storeFile(File file) throws IOException;
}
