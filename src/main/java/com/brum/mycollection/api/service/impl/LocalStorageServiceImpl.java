package com.brum.mycollection.api.service.impl;

import com.brum.mycollection.api.service.StorageService;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class LocalStorageServiceImpl implements StorageService {

    private static final Logger log = LoggerFactory.getLogger(LocalStorageServiceImpl.class);

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Value("${file.base-url}")
    private String baseUrl;

    @PostConstruct
    public void init() {
        try {
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
                log.info("Created upload directory: {}", uploadPath.toAbsolutePath());
            }
        } catch (IOException e) {
            log.error("Could not create upload directory: {}", e.getMessage());
            throw new RuntimeException("Could not create upload directory", e);
        }
    }

    @Override
    public String storeFile(MultipartFile file, String fileName) {
        try {
            String extension = getExtension(file.getOriginalFilename());
            String fullFileName = fileName + extension;
            Path targetPath = Paths.get(uploadDir).resolve(fullFileName);

            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

            String fileUrl = baseUrl + fullFileName;
            log.info("File stored successfully: {}", fileUrl);

            return fileUrl;
        } catch (IOException e) {
            log.error("Failed to store file: {}", e.getMessage());
            throw new RuntimeException("Failed to store file", e);
        }
    }

    @Override
    public String getFileUrl(String fileName) {
        return baseUrl + fileName;
    }

    @Override
    public void deleteFile(String fileUrl) {
        try {
            if (fileUrl == null || fileUrl.isEmpty()) {
                return;
            }

            String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
            Path filePath = Paths.get(uploadDir).resolve(fileName);

            if (Files.deleteIfExists(filePath)) {
                log.info("File deleted successfully: {}", fileName);
            } else {
                log.warn("File not found for deletion: {}", fileName);
            }
        } catch (IOException e) {
            log.error("Failed to delete file: {}", e.getMessage());
        }
    }

    private String getExtension(String originalFilename) {
        if (originalFilename == null || !originalFilename.contains(".")) {
            return ".jpg";
        }
        return originalFilename.substring(originalFilename.lastIndexOf("."));
    }
}
