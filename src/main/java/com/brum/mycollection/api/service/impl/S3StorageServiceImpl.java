package com.brum.mycollection.api.service.impl;

import com.brum.mycollection.api.entity.Item;
import com.brum.mycollection.api.service.S3StorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

@Service
public class S3StorageServiceImpl implements S3StorageService {
    private final S3Client s3Client;
    private final String bucketName;


    public S3StorageServiceImpl(S3Client s3Client,
                                @Value("${aws.s3.bucket-name}") String bucketName) {
        this.s3Client = s3Client;
        this.bucketName = bucketName;
    }
    @Override
    public String storeFile(MultipartFile file, String fileName) {
        try {
            System.out.println("Iniciando upload para S3...");
            System.out.println("Nome do arquivo original: " + file.getOriginalFilename());
            System.out.println("Nome do arquivo a ser usado: " + fileName);
            String fileExtension = getFileExtension(file.getOriginalFilename());


            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .contentType(file.getContentType())
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

            fileName = String.format("https://%s.s3.amazonaws.com/%s", bucketName, fileName);

            System.out.println("Upload concluído com sucesso!");
            System.out.println("URL do arquivo: " + String.format("https://%s.s3.amazonaws.com/%s", bucketName, fileName));
            return fileName;
        } catch (IOException ex) {
            System.err.println("Erro de IO durante o upload: " + ex.getMessage());
            throw new RuntimeException("Could not store file in S3. IO Error: " + ex.getMessage(), ex);
        } catch (Exception ex) {
            System.err.println("Erro durante o upload para S3: " + ex.getMessage());
            throw new RuntimeException("Could not store file in S3. Error: " + ex.getMessage(), ex);
        }
    }


    @Override
    public String getFileUrl(String fileName) {
        return String.format("https://%s.s3.amazonaws.com/%s", bucketName, fileName);
    }

    private String getFileExtension(String fileName) {
        if (fileName == null || fileName.lastIndexOf(".") == -1) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf("."));
    }
}
