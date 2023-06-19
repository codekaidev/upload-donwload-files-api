package com.codekaidev.uploaddownloadfiles.entity.dto;

import com.codekaidev.uploaddownloadfiles.shared.IsActive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MetadataFileDTO {

    private Long id;
    private String fileName;
    private Long fileSize;
    private String mimeType;
    private String fileLocation;
    private IsActive isActive;

}
