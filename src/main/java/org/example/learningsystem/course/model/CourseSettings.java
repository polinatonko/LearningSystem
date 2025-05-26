package org.example.learningsystem.course.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Table(name = "course_settings")
@Builder
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@DynamicInsert
public class CourseSettings {

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