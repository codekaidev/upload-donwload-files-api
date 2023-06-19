package com.codekaidev.uploaddownloadfiles.entity;

import com.codekaidev.uploaddownloadfiles.shared.IsActive;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity(name = "MetadataFile")
@Table(name = "metadata_file")
public class MetadataFileEntity extends BaseEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "file_name", unique = true)
    private String fileName;
    @Column(name = "file_size")
    private Long fileSize;
    @Column(name = "mime_type")
    private String mimeType;
    //store the file location. the path to local storage ; then S3 URL to S3 storage
    @Column(name = "file_location", nullable = false, unique = true)
    private String fileLocation;
    @Convert(converter = IsActiveConverter.class)
    @Column(name = "is_active")
    private IsActive isActive;

    @Builder
    public MetadataFileEntity(LocalDateTime createdAt, String createdBy, LocalDateTime modifyAt, String modifyBy,
                              Long id,
                              String fileName, Long fileSize, String mimeType, String fileLocation, IsActive isActive) {
        super(createdAt, createdBy, modifyAt, modifyBy);
        this.id = id;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.mimeType = mimeType;
        this.fileLocation = fileLocation;
        this.isActive = isActive;
    }

    @Converter(autoApply = true)
    public static class IsActiveConverter implements AttributeConverter<IsActive, String> {
        @Override
        public String convertToDatabaseColumn(IsActive isActive) {
            return isActive != null ? isActive.getStatus() : null;
        }

        @Override
        public IsActive convertToEntityAttribute(String value) {
            for (IsActive item : IsActive.values()) {
                if (item.getStatus().equals(value)) {
                    return item;
                }
            }
            throw new IllegalArgumentException("Invalid value: " + value);
        }
    }

}

