package org.example.learningsystem.core.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.UUID;

import static jakarta.persistence.GenerationType.UUID;

@MappedSuperclass
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = UUID)
    private UUID id;
    @Column(nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime created;
    @CreatedBy
    private String createdBy;
    @Column(nullable = false)
    @LastModifiedDate
    private LocalDateTime lastChanged;
    @LastModifiedBy
    private String lastChangedBy;
}
