package com.example.file_storage_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the File Storage Service application.
 *
 * This microservice is responsible for handling physical file storage:
 * - Uploading files (possibly in chunks for large files)
 * - Downloading files
 * - Managing file persistence (e.g., storing in local filesystem, S3, etc.)
 *
 * It will typically work together with the Document Metadata Service:
 * - Metadata Service stores document information (title, uploader, etc.)
 * - File Storage Service stores the actual file content
 */
@SpringBootApplication // Enables auto-configuration, component scanning, and Spring Boot setup
public class FileStorageServiceApplication {

    /**
     * Main method used to bootstrap the File Storage Service.
     * 
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(FileStorageServiceApplication.class, args);
    }

}
