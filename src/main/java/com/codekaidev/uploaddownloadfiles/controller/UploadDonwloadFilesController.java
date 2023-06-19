package com.codekaidev.uploaddownloadfiles.controller;

import com.codekaidev.uploaddownloadfiles.entity.dto.MetadataFileDTO;
import com.codekaidev.uploaddownloadfiles.service.IUploadDownloadFileService;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

import static java.nio.file.Paths.get;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/upload-download-file")
@CrossOrigin(origins = "http://localhost:4200",
        exposedHeaders = {"Origin", "Content-Type", "Accept", "Jwt-Token", "Authorization",
        "Access-Control-Allow-Origin", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials", "File-Name"})
public class UploadDonwloadFilesController {

    private final Map<String, IUploadDownloadFileService> fileManageServiceMap;

    /**
     * Endpoint for uploading multiple files
     *
     * @param uploadProvider Allows you to assign the file upload provider. uploadDownloadAWSS3StorageService, to
     *                       upload files to an S3; uploadDownloadLocalStorageService, to upload files to a local
     *                       directory
     * @param username       Identifies the user who uploads the files
     * @return list of files that were successfully uploaded
     */
    @GetMapping(value = "list/{username}")
    public ResponseEntity<List<MetadataFileDTO>> getUploadedFilesByUsername(
            @RequestHeader(value = "Upload-Provider", required = true) String uploadProvider,
            @PathVariable(value = "username", required = true) String username
    ) {
        IUploadDownloadFileService fileManageService = fileManageServiceMap.get(uploadProvider);
        return ResponseEntity.ok(fileManageService.getUploadedFilesByUsername(username));
    }

    /**
     * Endpoint for uploading multiple files
     *
     * @param multipartFiles Files that will be loaded according to the type of provider that is chosen
     * @param uploadedBy     Identifies the user who uploads the files
     * @param uploadProvider Allows you to assign the file upload provider. uploadDownloadAWSS3StorageService, to
     *                       upload files to an S3; uploadDownloadLocalStorageService, to upload files to a local
     *                       directory
     * @return list of files that were successfully uploaded
     */
    @PostMapping(value = "upload")
    public ResponseEntity<List<MetadataFileDTO>> uploadFile(
            @RequestPart(value = "files", required = true) @Size(max = 419430400) List<MultipartFile> multipartFiles,
            @RequestPart(value = "uploadedBy", required = true) String uploadedBy,
            @RequestHeader(value = "Upload-Provider", required = true) String uploadProvider
    ) {
        IUploadDownloadFileService fileManageService = fileManageServiceMap.get(uploadProvider);
        return ResponseEntity.ok().body(fileManageService.uploadFile(multipartFiles, uploadedBy));
    }

    /**
     * Endpoint to download a file based on its key (name of the file that was uploaded)
     *
     * @param fileName       Key that identifies the file to be downloaded
     * @param uploadProvider Allows you to assign the file upload provider. uploadDownloadAWSS3StorageService, to
     *                       upload files to an S3; uploadDownloadLocalStorageService, to upload files to a local
     *                       directory
     * @return An array of bytes representing the content of the downloaded file
     */
    @GetMapping(value = "download/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("fileName") String fileName,
                                                 @RequestHeader(value = "Upload-Provider", required = true) String uploadProvider) {
        IUploadDownloadFileService fileManageService = fileManageServiceMap.get(uploadProvider);
        Resource resource = new InputStreamResource(fileManageService.downloadFile(fileName));
        HttpHeaders headers = new HttpHeaders();
        headers.add("File-Name", fileName);
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");
        //Get media type
        MediaType mediaType = null;
        try {
            mediaType = MediaType.parseMediaType(Files.probeContentType(get(fileName)));
        } catch (IOException e) {
            mediaType = MediaType.APPLICATION_OCTET_STREAM;
        }
        return ResponseEntity
                .ok()
                .contentType(mediaType)
                .headers(headers)
                .body(resource);
    }

}
