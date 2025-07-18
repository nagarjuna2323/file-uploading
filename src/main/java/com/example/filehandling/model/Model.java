package com.example.filehandling.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "Uploaded_files")
public class Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;
    private String fileType;
    private LocalDateTime uploadedAt;
    private String description;
    private String extension;
    private Long size;
    private String filePath;


    @Lob // Large Object to handle file data
    private byte[] data;

    public Model() {}
    
    public Model(String fileName, String fileType, byte[] data, LocalDateTime uploadedAt, String description, String extension, Long size, String filePath) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.data = data;
        this.uploadedAt = uploadedAt;
        this.description = description;
        this.extension = extension;
        this.size = size;
        this.filePath = filePath;
    }

    // Getters and Setters

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
    
    public String getFileType() {
        return fileType;
    }
    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
    
    public byte[] getData() {
        return data;
    }
    public void setData(byte[] data) {
        this.data = data;
    }
    
    public LocalDateTime getUploadedAt() {
        return uploadedAt;
    }
    public void setUploadedAt(LocalDateTime uploadedAt) {
        this.uploadedAt = uploadedAt;
    }
    
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getExtension() {
        return extension;
    }
    public void setExtension(String extension) {
        this.extension = extension;
    }
    
    public Long getSize() {
        return size;
    }
    
    public void setSize(Long size){
        this.size = size;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}