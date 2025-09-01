package com.example.document_metadata_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Entry point for the Document Metadata Service application.
 *
 * This microservice is responsible for handling CRUD operations 
 * related to document metadata (e.g., title, description, owner, tags, etc.).
 * 
 * It registers itself with the Eureka Discovery Server so that 
 * other microservices can find and communicate with it.
 */
@SpringBootApplication  // Marks this as a Spring Boot application with auto-configuration & component scanning
@EnableDiscoveryClient  // Enables service registration with a Discovery Server (Eureka, Consul, or Zookeeper)
public class DocumentMetadataServiceApplication {

    /**
     * Main method to bootstrap and launch the Spring Boot application.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(DocumentMetadataServiceApplication.class, args);
    }

}
