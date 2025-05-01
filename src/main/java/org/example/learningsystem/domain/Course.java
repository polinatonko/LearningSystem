package org.example.learningsystem.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "course")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString(exclude = {"settings", "lessons", "enrollments"})
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String title;
    private String description;
    private BigDecimal price = BigDecimal.ZERO;
    private BigDecimal coinsPaid = BigDecimal.ZERO;
    @OneToOne(mappedBy = "course", cascade = CascadeType.ALL)
    private CourseSettings settings;
    @OneToMany(mappedBy = "course")
    private Set<Lesson> lessons;
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private Set<Enrollment> enrollments;

    public void setSettings(CourseSettings settings) {
        this.settings = settings;
        settings.setCourse(this);
    }
}