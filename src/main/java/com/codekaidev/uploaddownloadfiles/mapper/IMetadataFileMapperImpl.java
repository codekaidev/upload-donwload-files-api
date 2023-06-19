package com.codekaidev.uploaddownloadfiles.mapper;

import com.codekaidev.uploaddownloadfiles.entity.MetadataFileEntity;
import com.codekaidev.uploaddownloadfiles.entity.dto.MetadataFileDTO;
import org.springframework.stereotype.Component;

import javax.annotation.processing.Generated;

@Component
@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-06-15T10:50:18-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.5 (Oracle Corporation)"
)
public class IMetadataFileMapperImpl implements IMetadataFileMapper {

    @Override
    public MetadataFileDTO toMetadataFileDTO(MetadataFileEntity metadataFile) {
        if ( metadataFile == null ) {
            return null;
        }

        MetadataFileDTO metadataFileDTO = new MetadataFileDTO();

        metadataFileDTO.setId( metadataFile.getId() );
        metadataFileDTO.setFileName( metadataFile.getFileName() );
        metadataFileDTO.setFileSize( metadataFile.getFileSize() );
        metadataFileDTO.setMimeType( metadataFile.getMimeType() );
        metadataFileDTO.setFileLocation( metadataFile.getFileLocation() );
        metadataFileDTO.setIsActive( metadataFile.getIsActive() );

        return metadataFileDTO;
    }
}
