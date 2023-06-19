package com.codekaidev.uploaddownloadfiles.repository;

import com.codekaidev.uploaddownloadfiles.entity.MetadataFileEntity;
import com.codekaidev.uploaddownloadfiles.shared.IsActive;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IMetadataFileRepository extends CrudRepository<MetadataFileEntity, Long> {

    @Query(value = "SELECT m FROM MetadataFile m WHERE m >= :createAtP")
    List<MetadataFileEntity> findByCreateAtRange(@Param("createAtP") LocalDateTime createAt);

    @Query(value = "SELECT m FROM MetadataFile m WHERE m.createdBy = :username")
    List<MetadataFileEntity> findByUsername(@Param("username")String username);

    @Query(value = "UPDATE MetadataFile m SET m.isActive = :isActive WHERE m.id = :metadataFileId ")
    @Modifying
    int inactiveMetadataFile(@Param("metadataFileId") long metadataFileId, @Param("isActive")IsActive isActive);
}
