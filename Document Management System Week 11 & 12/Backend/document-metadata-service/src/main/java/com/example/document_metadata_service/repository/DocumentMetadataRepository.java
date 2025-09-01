package com.example.document_metadata_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.document_metadata_service.entity.DocumentMetadata;

/**
 * Repository interface for DocumentMetadata entity.
 *
 * Extends JpaRepository to provide built-in CRUD operations:
 *   - save(), findById(), findAll(), deleteById(), etc.
 *
 * Also defines a custom finder method using Spring Data JPA’s
 * query derivation mechanism.
 */
public interface DocumentMetadataRepository extends JpaRepository<DocumentMetadata, Long> {

    /**
     * Custom query method to search documents by title.
     * 
     * Spring Data JPA automatically translates this into:
     *   SELECT * FROM document_metadata 
     *   WHERE LOWER(title) LIKE LOWER('%{title}%');
     *
     * @param title part (or full) of the title to search for
     * @return list of matching DocumentMetadata records
     */
    List<DocumentMetadata> findByTitleContainingIgnoreCase(String title);
}
