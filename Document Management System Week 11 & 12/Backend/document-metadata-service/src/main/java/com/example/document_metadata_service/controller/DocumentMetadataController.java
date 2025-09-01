package com.example.document_metadata_service.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.document_metadata_service.entity.DocumentMetadata;
import com.example.document_metadata_service.service.DocumentMetadataService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * REST Controller for managing Document Metadata.
 *
 * Exposes CRUD + search endpoints for document metadata objects.
 * Example metadata could include: title, description, tags, owner, etc.
 */
@RestController
@RequestMapping("/api/metadata")  // Base path for all metadata-related APIs
@RequiredArgsConstructor         // Lombok generates constructor for 'service' (final field)
public class DocumentMetadataController {

    // Service layer dependency injected via constructor
    private final DocumentMetadataService service;

    /**
     * Create a new document metadata entry.
     * @param document validated request body containing metadata
     * @return the saved DocumentMetadata object
     */
    @PostMapping
    public ResponseEntity<DocumentMetadata> create(@Valid @RequestBody DocumentMetadata document) {
        return ResponseEntity.ok(service.save(document));
    }

    /**
     * Retrieve a document metadata by ID.
     * @param id metadata ID
     * @return DocumentMetadata if found
     */
    @GetMapping("/{id}")
    public ResponseEntity<DocumentMetadata> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    /**
     * Retrieve all metadata records.
     * @return list of all DocumentMetadata entries
     */
    @GetMapping
    public ResponseEntity<List<DocumentMetadata>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    /**
     * Search metadata records by title.
     * @param title part or full title string
     * @return list of matching DocumentMetadata entries
     */
    @GetMapping("/search")
    public ResponseEntity<List<DocumentMetadata>> search(@RequestParam String title) {
        return ResponseEntity.ok(service.searchByTitle(title));
    }

    /**
     * Update an existing metadata record.
     * @param id metadata ID
     * @param document updated metadata details
     * @return updated DocumentMetadata object
     */
    @PutMapping("/{id}")
    public ResponseEntity<DocumentMetadata> update(@PathVariable Long id, @RequestBody DocumentMetadata document) {
        return ResponseEntity.ok(service.update(id, document));
    }

    /**
     * Delete metadata by ID.
     * @param id metadata ID
     * @return HTTP 204 No Content if deletion successful
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
