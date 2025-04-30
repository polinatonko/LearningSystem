package org.example.learningsystem.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString
public class CourseSettings {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Boolean isPublic = Boolean.FALSE;
}