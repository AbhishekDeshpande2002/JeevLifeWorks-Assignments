package com.example.document_metadata_service.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.document_metadata_service.entity.DocumentMetadata;
import com.example.document_metadata_service.repository.DocumentMetadataRepository;
import com.example.document_metadata_service.service.DocumentMetadataService;

import lombok.RequiredArgsConstructor;

/**
 * Implementation of the DocumentMetadataService interface.
 *
 * This class contains the business logic for handling
 * document metadata operations (CRUD + search).
 *
 * It interacts directly with the Repository layer.
 */
@Service                         // Marks this as a Spring-managed service bean
@RequiredArgsConstructor          // Lombok: generates constructor for final fields (repository)
public class DocumentMetadataServiceImpl implements DocumentMetadataService {

    private final DocumentMetadataRepository repository;

    /**
     * Save a new DocumentMetadata record.
     * Automatically sets the current timestamp as uploadDate.
     */
    @Override
    public DocumentMetadata save(DocumentMetadata document) {
        document.setUploadDate(LocalDateTime.now()); // Set upload time
        return repository.save(document);            // Persist to DB
    }

    /**
     * Find a document metadata entry by ID.
     * Throws RuntimeException if not found.
     */
    @Override
    public DocumentMetadata getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found with id " + id));
    }

    /**
     * Fetch all document metadata records.
     */
    @Override
    public List<DocumentMetadata> getAll() {
        return repository.findAll();
    }

    /**
     * Search for documents by title (case-insensitive).
     */
    @Override
    public List<DocumentMetadata> searchByTitle(String title) {
        return repository.findByTitleContainingIgnoreCase(title);
    }

    /**
     * Update an existing document metadata entry.
     * Only fields provided in the new object are updated.
     */
    @Override
    public DocumentMetadata update(Long id, DocumentMetadata document) {
        // Fetch existing record or throw exception if not found
        DocumentMetadata existing = getById(id);

        // Update fields
        existing.setTitle(document.getTitle());
        existing.setDescription(document.getDescription());
        existing.setFileName(document.getFileName());
        existing.setFileSize(document.getFileSize());
        existing.setUploadedBy(document.getUploadedBy());

        return repository.save(existing); // Save updated entity
    }

    /**
     * Delete a metadata record by ID.
     */
    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
