package com.codekaidev.uploaddownloadfiles.mapper;

import com.codekaidev.uploaddownloadfiles.entity.MetadataFileEntity;
import com.codekaidev.uploaddownloadfiles.entity.dto.MetadataFileDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Assigns the data from the MetadataFie entity to the DTO object
 * @author Daniel Caicedo
 * @version 0.0.1
 */
@Mapper(implementationPackage = "com.codekaidev.uploaddownloadfiles.mapper.impl")
public interface IMetadataFileMapper {

    /**
     * Assigns the data from the MetadataFie entity to the DTO object
     * @param metadataFile the entity object
     * @return the DTO object
     */
    @Mapping(source = "id", target = "id")
    @Mapping(source = "fileName", target = "fileName")
    @Mapping(source = "fileSize", target = "fileSize")
    @Mapping(source = "mimeType", target = "mimeType")
    @Mapping(source = "fileLocation", target = "fileLocation")
    @Mapping(source = "isActive", target = "isActive")
    MetadataFileDTO toMetadataFileDTO(MetadataFileEntity metadataFile);

}
