package org.example.learningsystem.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "course_settings")
@Builder
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class CourseSettings {
    @Id
    private UUID id;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Boolean isPublic = Boolean.FALSE;
    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private Course course;
}