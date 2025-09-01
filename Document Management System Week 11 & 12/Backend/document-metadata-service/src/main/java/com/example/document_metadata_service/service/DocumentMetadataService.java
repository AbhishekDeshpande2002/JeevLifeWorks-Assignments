package com.example.document_metadata_service.service;

import java.util.List;

import com.example.document_metadata_service.entity.DocumentMetadata;

/**
 * Service interface for managing Document Metadata.
 *
 * Defines the contract for business logic operations
 * that sit between the Controller (API layer) and
 * Repository (database access layer).
 */
public interface DocumentMetadataService {

    /**
     * Save a new document metadata entry.
     * 
     * @param document DocumentMetadata object to be saved
     * @return the saved DocumentMetadata object
     */
    DocumentMetadata save(DocumentMetadata document);

    /**
     * Retrieve metadata by ID.
     * 
     * @param id unique identifier of the document metadata
     * @return DocumentMetadata object if found
     */
    DocumentMetadata getById(Long id);

    /**
     * Retrieve all document metadata records.
     * 
     * @return list of all DocumentMetadata objects
     */
    List<DocumentMetadata> getAll();

    /**
     * Search for metadata records by title (case-insensitive).
     * 
     * @param title partial or full title string
     * @return list of matching DocumentMetadata objects
     */
    List<DocumentMetadata> searchByTitle(String title);

    /**
     * Update an existing metadata entry.
     * 
     * @param id ID of the document metadata to update
     * @param document new details to update
     * @return updated DocumentMetadata object
     */
    DocumentMetadata update(Long id, DocumentMetadata document);

    /**
     * Delete metadata by ID.
     * 
     * @param id ID of the document metadata to delete
     */
    void delete(Long id);
}
