package com.brum.mycollection.api.service.impl;

import com.brum.mycollection.api.service.S3StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;

@Slf4j
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
            log.info("Iniciando upload para S3...");
            log.info("Nome do arquivo original: " + file.getOriginalFilename());
            log.info("Nome do arquivo a ser usado: " + fileName);
            String fileExtension = getFileExtension(file.getOriginalFilename());


            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .contentType(file.getContentType())
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

            fileName = String.format("https://%s.s3.amazonaws.com/%s", bucketName, fileName);

            log.info("Upload concluído com sucesso!");
            log.info("URL do arquivo: " + String.format("https://%s.s3.amazonaws.com/%s", bucketName, fileName));
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

    @Override
    public void deleteFile(String fileName) {
        try {
            log.info("Iniciando exclusão do arquivo do S3...");
            log.info("Nome do arquivo a ser excluído: " + fileName);

            // Se a URL completa foi fornecida, extrair apenas o nome do arquivo
            final String fileKey = fileName.startsWith("https://") 
                ? fileName.substring(fileName.lastIndexOf("/") + 1)
                : fileName;

            s3Client.deleteObject(builder -> builder
                .bucket(bucketName)
                .key(fileKey)
                .build());

            log.info("Arquivo excluído com sucesso!");
        } catch (Exception ex) {
            System.err.println("Erro ao excluir arquivo do S3: " + ex.getMessage());
            throw new RuntimeException("Could not delete file from S3. Error: " + ex.getMessage(), ex);
        }
    }
}
