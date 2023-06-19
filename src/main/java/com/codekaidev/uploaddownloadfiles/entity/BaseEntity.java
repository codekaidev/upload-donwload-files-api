package com.codekaidev.uploaddownloadfiles.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class BaseEntity {

    @Column(name = "createAt")
    private LocalDateTime createdAt;

    @Column(name = "createdBy")
    private String createdBy;

    @Column(name = "modifyAt")
    private LocalDateTime modifyAt;

    @Column(name = "modifyBy")
    private String modifyBy;

}
