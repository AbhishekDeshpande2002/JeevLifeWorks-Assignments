package com.example.gateway_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication  // Marks this as a Spring Boot application (auto-configuration, component scanning, etc.)
public class GatewayServiceApplication {

    public static void main(String[] args) {
        // Entry point of the Spring Boot application
        SpringApplication.run(GatewayServiceApplication.class, args);
    }
}
