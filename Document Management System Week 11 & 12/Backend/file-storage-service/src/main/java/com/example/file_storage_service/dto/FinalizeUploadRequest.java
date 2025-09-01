package com.example.file_storage_service.dto;

import lombok.Data;

/**
 * DTO used to finalize a file upload once
 * all chunks have been successfully uploaded.
 *
 * The client will send this request to indicate
 * that the server can now merge all uploaded chunks
 * into a complete file.
 */
@Data   // Lombok generates getters, setters, equals, hashCode, and toString
public class FinalizeUploadRequest {

    /**
     * Unique identifier for the file being uploaded.
     * This must match the fileId used in all FileChunkRequest chunks.
     */
    private String fileId;

    /**
     * Original name of the file being uploaded.
     * This will be used when saving the file metadata
     * and when serving the file for download.
     */
    private String fileName;
}
