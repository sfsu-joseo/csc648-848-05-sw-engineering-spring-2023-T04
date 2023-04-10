package com.example.springbackendv2.model;

import javax.persistence.*;

@Entity
public class FileDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "filename")
    private String fileName;

    @Column(name = "docfile")
    @Lob
    private byte[] docFile;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getDocFile() {
        return docFile;
    }

    public void setDocFile(byte[] docFile) {
        this.docFile = docFile;
    }
}
