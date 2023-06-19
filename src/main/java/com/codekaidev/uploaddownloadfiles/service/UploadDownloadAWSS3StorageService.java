package com.codekaidev.uploaddownloadfiles.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.codekaidev.uploaddownloadfiles.config.AWSConfig;
import com.codekaidev.uploaddownloadfiles.mapper.IMetadataFileMapper;
import com.codekaidev.uploaddownloadfiles.entity.MetadataFileEntity;
import com.codekaidev.uploaddownloadfiles.entity.dto.MetadataFileDTO;
import com.codekaidev.uploaddownloadfiles.shared.IsActive;
import com.codekaidev.uploaddownloadfiles.util.date.LocalDateFormat;
import com.codekaidev.uploaddownloadfiles.util.date.LocalDateUtil;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UploadDownloadAWSS3StorageService implements IUploadDownloadFileService {

    private final AWSConfig awsConfig;
    private final AmazonS3 amazonS3;
    private final MetadaFileService metadaFileService;
    private final IMetadataFileMapper iMetadataFileMapper;

    @Override public List<MetadataFileDTO> uploadFile(List<MultipartFile> filesToUpload, String uploadedBy) {
        List<MetadataFileDTO> uploadedMetadataFiles = new ArrayList<>();
        for (MultipartFile fileToUpload :
                filesToUpload) {
            if (Strings.isBlank(fileToUpload.getOriginalFilename()))
                continue;
            try {
                String fileName = fileToUpload.getOriginalFilename();
                PutObjectRequest putObjectRequest = new PutObjectRequest(awsConfig.getBucket(), fileName,
                        fileToUpload.getInputStream(), null);
                amazonS3.putObject(putObjectRequest);
                String fileUrl = String.valueOf(amazonS3.getUrl(awsConfig.getBucket(), fileName));
                //Save metadata file in database
                //TODO: format to insert createAt db
                MetadataFileEntity metadataFile = MetadataFileEntity.builder()
                        .fileName(fileName)
                        .fileSize(fileToUpload.getSize())
                        .mimeType(Files.probeContentType(Path.of(fileName)))
                        .fileLocation(fileUrl)
                        .isActive(IsActive.YES)
                        .createdAt(LocalDateUtil.toLocalDateTimeFormat(LocalDateTime.now(),
                                LocalDateFormat.DD_MM_4Y_HH_MM_SS))
                        .createdBy(uploadedBy)
                        .build();
                System.out.println(LocalDateUtil.toLocalDateTimeFormat(LocalDateTime.now(),
                        LocalDateFormat.DD_MM_4Y_HH_MM_SS));
                System.out.println(LocalDateTime.now());
                metadataFile = metadaFileService.saveMetadataFile(metadataFile);
                uploadedMetadataFiles.add(iMetadataFileMapper.toMetadataFileDTO(metadataFile));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return uploadedMetadataFiles;
    }

    @Override public List<MetadataFileDTO> getUploadedFilesByUsername(String username) {
        return metadaFileService.findByUsername(username).stream().peek(m ->{
            if(!amazonS3.doesObjectExist(awsConfig.getBucket(), m.getFileName())){
                metadaFileService.inactiveMetadataFile(m.getId());
                m.setIsActive(IsActive.NO);
            };
        }).collect(Collectors.toList());
    }

    @Override
    public InputStream downloadFile(String fileName) {
        if(!amazonS3.doesObjectExist(awsConfig.getBucket(), fileName)){
            throw new RuntimeException("File not found");
        }
        S3Object s3Object = amazonS3.getObject(awsConfig.getBucket(), fileName);
        return s3Object.getObjectContent();
    }
}
