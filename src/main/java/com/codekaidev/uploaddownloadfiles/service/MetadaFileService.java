package com.codekaidev.uploaddownloadfiles.service;

import com.codekaidev.uploaddownloadfiles.mapper.IMetadataFileMapper;
import com.codekaidev.uploaddownloadfiles.entity.MetadataFileEntity;
import com.codekaidev.uploaddownloadfiles.entity.dto.MetadataFileDTO;
import com.codekaidev.uploaddownloadfiles.repository.IMetadataFileRepository;
import com.codekaidev.uploaddownloadfiles.shared.IsActive;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MetadaFileService implements IMetadataFileService{

    private final IMetadataFileRepository iMetadataFileRepository;
    private final IMetadataFileMapper iMetadataFileMapper;


    @Transactional @Override public List<MetadataFileDTO> findByCreateAtRange(LocalDateTime createAt) {
        return iMetadataFileRepository.findByCreateAtRange(createAt)
                .stream()
                .map(iMetadataFileMapper::toMetadataFileDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override public List<MetadataFileDTO> findByUsername(String username) {
        return iMetadataFileRepository.findByUsername(username)
                .stream()
                .map(iMetadataFileMapper::toMetadataFileDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override public int inactiveMetadataFile(long metadataFileId) {
        return iMetadataFileRepository.inactiveMetadataFile(metadataFileId, IsActive.NO);
    }


    @Transactional @Override public MetadataFileEntity saveMetadataFile(MetadataFileEntity metadataFile) {
        return iMetadataFileRepository.save(metadataFile);
    }
}
