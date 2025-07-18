package com.example.filehandling.service;

import com.example.filehandling.model.Model;
import com.example.filehandling.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface FileService {
    Model storeFile(MultipartFile file, String description) throws IOException;
    Optional<Model> getFile(Long id);
    List<Model> getAllFiles();
    byte[] downloadFile(Long id) throws IOException;
}

@Service
class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;

    private static final String UPLOAD_DIR = "uploads";
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5 MB

    @Autowired
    public FileServiceImpl(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Override
    public Model storeFile(MultipartFile file, String description) throws IOException {
        if (file.getSize() > MAX_FILE_SIZE) {
            throw new IOException("File exceeds maximum allowed size.");
        }

        String fileName = file.getOriginalFilename();
        String extension = fileName != null && fileName.contains(".") ? fileName.substring(fileName.lastIndexOf('.') + 1) : "unknown";
        String fileType = file.getContentType();
        long size = file.getSize();

        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Path filePath = uploadPath.resolve(fileName);
        Files.write(filePath, file.getBytes(), StandardOpenOption.CREATE);

        Model fileMeta = new Model(
                fileName,
                fileType,
                file.getBytes(),
                LocalDateTime.now(),
                description,
                extension,
                size,
                filePath.toString()
        );

        return fileRepository.save(fileMeta);
    }

    @Override
    public Optional<Model> getFile(Long id) {
        return fileRepository.findById(id);
    }

    @Override
    public List<Model> getAllFiles() {
        return fileRepository.findAll();
    }

    @Override
    public byte[] downloadFile(Long id) throws IOException {
        Optional<Model> modelOpt = fileRepository.findById(id);
        if (modelOpt.isPresent()) {
            Model file = modelOpt.get();
            Path path = Paths.get(file.getFilePath());
            return Files.readAllBytes(path);
        } else {
            throw new IOException("File not found.");
        }
    }
}
