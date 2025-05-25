package org.example.learningsystem.core.audit.model;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AuditableEntity {

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
