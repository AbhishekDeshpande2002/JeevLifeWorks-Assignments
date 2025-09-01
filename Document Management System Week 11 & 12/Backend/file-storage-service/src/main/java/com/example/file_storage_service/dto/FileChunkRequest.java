package com.example.file_storage_service.dto;

import lombok.Data;

/**
 * DTO (Data Transfer Object) representing a single file chunk
 * sent during the upload process.
 *
 * This allows large files to be uploaded in smaller parts
 * rather than in one large request, which improves scalability
 * and avoids memory issues.
 */
@Data   // Lombok generates getters, setters, toString, equals, and hashCode
public class FileChunkRequest {

    /**
     * Unique identifier for the file being uploaded.
     * This helps the system associate all chunks with the same file.
     * Could be a UUID, hash, or metadata ID.
     */
    private String fileId;

    /**
     * Current chunk number (e.g., 1, 2, 3...).
     * Helps in storing and later reassembling chunks in the correct order.
     */
    private int chunkNumber;

    /**
     * Total number of chunks expected for this file.
     * Useful for determining when upload is complete.
     */
    private int totalChunks;

    /**
     * Actual binary data of the chunk.
     * This holds the file content in bytes.
     */
    private byte[] data;
}
