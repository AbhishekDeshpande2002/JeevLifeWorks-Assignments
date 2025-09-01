package com.example.file_storage_service.service;

import java.io.FileNotFoundException;

import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;

import com.example.file_storage_service.dto.FileChunkRequest;
import com.example.file_storage_service.dto.FinalizeUploadRequest;

@Service
public class FileStorageService {

    @Value("${file.storage-location}")
    private String storageLocation; // Base folder to store files and chunks

    /**
     * Save an individual chunk to temporary storage.
     * Each chunk is saved as a separate file (chunk_1, chunk_2, etc.)
     * inside a directory named after the unique fileId.
     */
    public void saveChunk(FileChunkRequest request) throws IOException {
        // Create a directory for the file if it doesn’t already exist
        Path dirPath = Paths.get(storageLocation, request.getFileId()); 
        Files.createDirectories(dirPath);

        // Save the chunk to a file like "chunk_1", "chunk_2", etc.
        Path chunkPath = dirPath.resolve("chunk_" + request.getChunkNumber()); 
        Files.write(chunkPath, request.getData()); 
    }

    /**
     * Combine all chunks into the final file after upload completes.
     * Reads all "chunk_x" files in order and writes them sequentially into the final file.
     * After merging, deletes the temporary chunk directory.
     */
    public void finalizeUpload(FinalizeUploadRequest request) throws IOException {
        // Directory where chunks are stored (e.g., /storage/fileId/)
        Path dirPath = Paths.get(storageLocation, request.getFileId());

        // Final file path where all chunks will be merged (e.g., /storage/filename.pdf)
        Path finalFilePath = Paths.get(storageLocation, request.getFileName()); 

        try (OutputStream outputStream = Files.newOutputStream(finalFilePath)) {
            int chunkNumber = 1;

            // Keep reading chunks sequentially until no more exist
            while (true) {
                Path chunkPath = dirPath.resolve("chunk_" + chunkNumber); 
                if (!Files.exists(chunkPath)) break; // Stop if next chunk not found
                Files.copy(chunkPath, outputStream); // Append chunk data into final file
                chunkNumber++;
            }
        }

        // Delete temporary chunks folder once file has been merged successfully
        FileSystemUtils.deleteRecursively(dirPath);
    }

    /**
     * Retrieve a specific chunk of a file for download (chunked streaming).
     * Reads only the required part of the file instead of loading the entire file into memory.
     */
    public byte[] getChunk(String fileName, int chunkNumber, int chunkSize) throws IOException {
        Path filePath = Paths.get(storageLocation, fileName);

        // If file doesn’t exist, throw error
        if (!Files.exists(filePath)) {
            throw new FileNotFoundException("File not found: " + fileName);
        }

        try (RandomAccessFile raf = new RandomAccessFile(filePath.toFile(), "r")) {
            // Calculate starting byte of this chunk
            long offset = (long) (chunkNumber - 1) * chunkSize;

            // If offset is beyond file length, return empty byte array
            if (offset >= raf.length()) {
                return new byte[0]; 
            }

            // Move pointer to start position of chunk
            raf.seek(offset);

            // Allocate buffer of chunk size
            byte[] buffer = new byte[chunkSize];

            // Read up to chunkSize bytes
            int bytesRead = raf.read(buffer); 

            // If last chunk is smaller than chunkSize, trim extra bytes
            if (bytesRead < chunkSize) {
                return Arrays.copyOf(buffer, bytesRead); 
            }
            return buffer;
        }
    }
}
