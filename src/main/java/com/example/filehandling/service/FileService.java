package com.example.filehandling.service;

import com.example.filehandling.model.Model;
import com.example.filehandling.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface FileService {
    Model storeFile(MultipartFile file, String description) throws IOException;

    Optional<Model> getFile(Long id);

    List<Model> getAllFiles();
}

@Service
class FileServiceImpl implements FileService {

    @Autowired
    private FileRepository fileRepository;

    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5 MB

    @Override
    public Model storeFile(MultipartFile file, String description) throws IOException {
        String fileName = file.getOriginalFilename();
        String fileType = file.getContentType();
        byte[] data = file.getBytes();
        String extension = fileName != null && fileName.contains(".") ? fileName.substring(fileName.lastIndexOf('.') + 1) : "unknown";
        Long size = file.getSize();

        if (size > MAX_FILE_SIZE) {
            throw new IOException("File is more than 5 MB");
        }

        Model fileModel = new Model(fileName, fileType, data, LocalDateTime.now(), description, extension, size);
        return fileRepository.save(fileModel);
    }

    @Override
    public Optional<Model> getFile(Long id) {
        return fileRepository.findById(id);
    }

    @Override
    public List<Model> getAllFiles() {
        return fileRepository.findAll();
    }
}
