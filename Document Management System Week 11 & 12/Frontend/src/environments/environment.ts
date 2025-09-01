export const environment = {
  production: false,
  apiMetadataBase: 'http://localhost:8081/api/metadata', // metadata service or gateway route
  apiFilesBase: 'http://localhost:8082/api/files',      // file storage service or gateway route
  chunkSize: 1024 * 1024 // 1 MB
};
