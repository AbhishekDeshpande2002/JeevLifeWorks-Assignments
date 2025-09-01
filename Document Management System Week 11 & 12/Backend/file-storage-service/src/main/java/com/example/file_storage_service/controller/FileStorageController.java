package com.example.file_storage_service.controller;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.file_storage_service.dto.FileChunkRequest;
import com.example.file_storage_service.dto.FinalizeUploadRequest;
import com.example.file_storage_service.service.FileStorageService;

import lombok.RequiredArgsConstructor;

/**
 * REST Controller for file storage operations.
 *
 * This controller supports:
 *  - Uploading large files in chunks
 *  - Finalizing an upload (merging chunks into a single file)
 *  - Downloading files in chunks
 *
 * This makes the service scalable and memory-efficient,
 * especially for very large files.
 */
@RestController
@RequestMapping("/api/files")   // Base path for all file-related endpoints
@RequiredArgsConstructor        // Lombok: generates constructor for FileStorageService
public class FileStorageController {

    private final FileStorageService storageService;

    /**
     * Upload a single chunk of a file.
     *
     * @param request contains file data and chunk details
     * @return success message with chunk number
     */
    @PostMapping("/upload-chunk")
    public ResponseEntity<String> uploadChunk(@RequestBody FileChunkRequest request) throws IOException {
        storageService.saveChunk(request);
        return ResponseEntity.ok("Chunk " + request.getChunkNumber() + " uploaded successfully");
    }

    /**
     * Finalize the upload after all chunks are uploaded.
     * The service should merge all chunks into a complete file.
     *
     * @param request contains file info (file name, total chunks, etc.)
     * @return success message with file name
     */
    @PostMapping("/finalize-upload")
    public ResponseEntity<String> finalizeUpload(@RequestBody FinalizeUploadRequest request) throws IOException {
        storageService.finalizeUpload(request);
        return ResponseEntity.ok("File upload completed: " + request.getFileName());
    }

    /**
     * Download a specific chunk of a file.
     *
     * @param fileName name of the file being downloaded
     * @param chunkNumber which chunk to download
     * @param chunkSize size of each chunk (default 1 MB = 1048576 bytes)
     * @return byte array representing the requested chunk
     */
    @GetMapping("/download-chunk/{fileName}/{chunkNumber}")
    public ResponseEntity<byte[]> downloadChunk(
            @PathVariable String fileName,
            @PathVariable int chunkNumber,
            @RequestParam(defaultValue = "1048576") int chunkSize) throws IOException {
        
        byte[] data = storageService.getChunk(fileName, chunkNumber, chunkSize);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM) // Binary response
                .body(data);
    }
}
