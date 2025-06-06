package org.example.learningsystem.course.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.example.learningsystem.core.audit.model.AuditableEntity;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Table(name = "course_settings")
@SuperBuilder
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
@NoArgsConstructor
@ToString
@DynamicInsert
public class CourseSettings extends AuditableEntity {

    @Id
    private UUID id;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private Boolean isPublic;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private Course course;
}