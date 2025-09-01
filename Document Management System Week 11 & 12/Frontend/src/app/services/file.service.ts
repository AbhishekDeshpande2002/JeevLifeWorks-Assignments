import { Injectable } from '@angular/core';
import { HttpClient, HttpRequest } from '@angular/common/http';
import { firstValueFrom } from 'rxjs';
import { saveAs } from 'file-saver';
import { v4 as uuidv4 } from 'uuid';
import { environment } from '../../environments/environment';

@Injectable({ providedIn: 'root' })
export class FileService {
  base = environment.apiFilesBase;   // Base API URL for file operations
  chunkSize = environment.chunkSize; // Configurable chunk size for uploads

  constructor(private http: HttpClient) {}

  // Generate a unique ID for a file (used to track chunks)
  generateFileId(): string {
    return uuidv4();
  }

  // Upload a single chunk of the file
  uploadChunk(fileId: string, chunk: Blob, chunkNumber: number, totalChunks: number) {
    const fd = new FormData();
    fd.append('fileId', fileId);                         // Unique file identifier
    fd.append('chunkNumber', String(chunkNumber));       // Current chunk number
    fd.append('totalChunks', String(totalChunks));       // Total chunks in the file
    fd.append('file', chunk, `chunk-${chunkNumber}`);    // Actual file chunk

    // Use HttpRequest to enable progress tracking
    const req = new HttpRequest('POST', `${this.base}/upload-chunk`, fd, { reportProgress: true });
    return firstValueFrom(this.http.request(req));
  }

  // Notify backend that all chunks are uploaded and file assembly can be finalized
  finalizeUpload(fileId: string, fileName: string) {
    return firstValueFrom(this.http.post(`${this.base}/finalize-upload`, { fileId, fileName }));
  }

  // Fetch metadata about the file (size, type, chunks, etc.)
  getFileMeta(fileId: string) {
    return firstValueFrom(this.http.get<any>(`${this.base}/meta/${fileId}`));
  }

  // Download a single chunk by fileId and chunk number
  downloadChunk(fileId: string, chunkNumber: number) {
    return firstValueFrom(
      this.http.get(`${this.base}/download-chunk/${fileId}/${chunkNumber}`, { responseType: 'blob' })
    );
  }

  // Download all chunks of a file sequentially, merge them, and save the final file
  async downloadFileFromChunks(fileId: string, fileName: string, totalChunks: number, contentType = 'application/octet-stream') {
    const blobs: Blob[] = [];

    // Download chunks one by one and store them
    for (let i = 0; i < totalChunks; i++) {
      const chunk = await this.downloadChunk(fileId, i);
      blobs.push(chunk as Blob);
    }

    // Merge all chunks into a single Blob
    const full = new Blob(blobs, { type: contentType });

    // Trigger file download in browser
    saveAs(full, fileName);
  }
}
