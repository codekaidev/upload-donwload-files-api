package com.codekaidev.uploaddownloadfiles.service;

import com.codekaidev.uploaddownloadfiles.entity.MetadataFileEntity;
import com.codekaidev.uploaddownloadfiles.entity.dto.MetadataFileDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface IMetadataFileService {

    List<MetadataFileDTO> findByCreateAtRange(LocalDateTime createAt);
    List<MetadataFileDTO> findByUsername(String username);

    int inactiveMetadataFile(long metadataFileId);
    MetadataFileEntity saveMetadataFile(MetadataFileEntity metadataFile);

}
