package com.brum.mycollection.api.service;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    String storeFile(MultipartFile file, String fileName);
    String getFileUrl(String fileName);
    void deleteFile(String fileName);
}
