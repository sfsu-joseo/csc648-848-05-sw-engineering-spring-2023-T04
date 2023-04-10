package com.example.springbackendv2.repository;

import com.example.springbackendv2.model.FileDocument;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocFileDao extends CrudRepository<FileDocument, Long> {


    FileDocument findByFileName(String fileName);
}

