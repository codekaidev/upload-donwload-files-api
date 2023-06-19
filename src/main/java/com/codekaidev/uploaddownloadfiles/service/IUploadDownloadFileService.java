package com.codekaidev.uploaddownloadfiles.service;

import com.codekaidev.uploaddownloadfiles.entity.dto.MetadataFileDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

/**
 * Define the methods that will be used to manage file operations.
 * @author Daniel Caicedo
 * @version 0.0.1
 */
public interface IUploadDownloadFileService {

    /**
     * File upload process that receives the multipart files to be uploaded
     * @param filesToUpload file will be uploaded
     * @param uploadedBy the representation of the client that executes the load action
     * @return the list of metadata files that were loaded
     */
    List<MetadataFileDTO> uploadFile(List<MultipartFile> filesToUpload,String uploadedBy);

    /**
     * Gets the list of uploaded metadata files by username or the client that uploaded the files
     * @param username username or the client that uploaded the files
     * @return the list of metadata files that were loaded
     */
    List<MetadataFileDTO> getUploadedFilesByUsername(String username);

    /**
     * Download a specific file by file name
     * @param fileName La clave del archivo a descargar
     * @return the input byte stream of the file
     */
    InputStream downloadFile(String fileName);

}
