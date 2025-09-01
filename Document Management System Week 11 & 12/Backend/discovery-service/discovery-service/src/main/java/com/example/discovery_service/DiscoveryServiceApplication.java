package com.example.discovery_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Entry point for the Discovery Service application.
 * 
 * This service acts as a Eureka Server in a microservices architecture.
 * Other microservices (clients) will register themselves here,
 * and they can also discover each other through this server.
 */
@SpringBootApplication // Marks this as a Spring Boot application (auto-configuration, component scanning, etc.)
@EnableEurekaServer   // Enables Eureka Server functionality for service discovery
public class DiscoveryServiceApplication {

    /**
     * Main method to bootstrap and launch the Spring Boot application.
     * 
     * @param args application arguments passed during startup
     */
    public static void main(String[] args) {
        SpringApplication.run(DiscoveryServiceApplication.class, args);
    }

}
