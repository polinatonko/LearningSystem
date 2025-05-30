package org.example.learningsystem.core.audit.model;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@SuperBuilder(toBuilder = true)
@Getter
@Setter
@NoArgsConstructor
public class AuditableEntity {

    @Column(nullable = false, updatable = false)
    @CreatedDate
    private Instant created;

    @CreatedBy
    private String createdBy;

    @Column(nullable = false)
    @LastModifiedDate
    private Instant lastChanged;

    @LastModifiedBy
    private String lastChangedBy;
}
