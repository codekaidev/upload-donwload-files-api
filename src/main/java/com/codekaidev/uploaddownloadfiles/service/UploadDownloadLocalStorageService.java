package com.codekaidev.uploaddownloadfiles.service;

import com.codekaidev.uploaddownloadfiles.mapper.IMetadataFileMapper;
import com.codekaidev.uploaddownloadfiles.entity.MetadataFileEntity;
import com.codekaidev.uploaddownloadfiles.entity.dto.MetadataFileDTO;
import com.codekaidev.uploaddownloadfiles.util.date.LocalDateFormat;
import com.codekaidev.uploaddownloadfiles.util.date.LocalDateUtil;
import com.codekaidev.uploaddownloadfiles.shared.IsActive;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.nio.file.Files.copy;
import static java.nio.file.Paths.get;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@RequiredArgsConstructor
@Service
public class UploadDownloadLocalStorageService implements IUploadDownloadFileService {

    @Value("${app.directorystorage}")
    private String directoryStorage;

    private final MetadaFileService metadaFileService;

    private final ResourceLoader resourceLoader;

    private final IMetadataFileMapper iMetadataFileMapper;

    @Override
    public List<MetadataFileDTO> uploadFile(List<MultipartFile> filesToUpload, String uploadedBy) {
        List<MetadataFileDTO> uploadedFiles = new ArrayList<>();
        for (MultipartFile fileToUpload:
             filesToUpload) {
            if(Strings.isBlank(fileToUpload.getOriginalFilename()))
                continue;
            String fileName = StringUtils.cleanPath(fileToUpload.getOriginalFilename());
            Path path = get(directoryStorage, fileName).toAbsolutePath().normalize();
            try {
                copy(fileToUpload.getInputStream(), path, REPLACE_EXISTING);
                //Save metadata file in database
                MetadataFileEntity metadataFile = MetadataFileEntity.builder()
                        .fileName(fileName)
                        .fileSize(fileToUpload.getSize())
                        .mimeType(Files.probeContentType(path))
                        .fileLocation(path.toString())
                        .isActive(IsActive.YES)
                        .createdAt(LocalDateUtil.toLocalDateTimeFormat(LocalDateTime.now(), LocalDateFormat.DD_MM_4Y_HH_MM_SS))
                        .createdBy(uploadedBy)
                        .build();
                System.out.println(LocalDateUtil.toLocalDateTimeFormat(LocalDateTime.now(), LocalDateFormat.DD_MM_4Y_HH_MM_SS));
                metadataFile = metadaFileService.saveMetadataFile(metadataFile);
                uploadedFiles.add(iMetadataFileMapper.toMetadataFileDTO(metadataFile));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return uploadedFiles;
    }

    @Override
    public List<MetadataFileDTO> getUploadedFilesByUsername(String username) {
        return metadaFileService.findByUsername(username).stream().peek(m ->{
            if(!Files.exists(Paths.get(directoryStorage, m.getFileName()))){
                metadaFileService.inactiveMetadataFile(m.getId());
                m.setIsActive(IsActive.NO);
            };
        }).collect(Collectors.toList());
    }

    @Override
    public InputStream downloadFile(String fileName){
        Path path = get(directoryStorage).toAbsolutePath().normalize().resolve(fileName);
        if (!Files.exists(path)) {
            throw new RuntimeException(String.format("The file %s cant be found", fileName));
        }
        Resource resource = null;
        try {
            resource = new UrlResource(path.toUri());
            return resource.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
