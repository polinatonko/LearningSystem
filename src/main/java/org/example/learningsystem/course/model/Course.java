package org.example.learningsystem.course.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.example.learningsystem.core.audit.model.AuditableEntity;
import org.example.learningsystem.lesson.model.Lesson;
import org.hibernate.annotations.DynamicInsert;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.GenerationType.UUID;

@Entity
@Table(name = "course")
@SuperBuilder(toBuilder = true)
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"settings", "lessons", "enrollments"})
@DynamicInsert
public class Course extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = UUID)
    private UUID id;

    private String title;

    private String description;

    private BigDecimal price;

    private BigDecimal coinsPaid;

    @OneToOne(mappedBy = "course", cascade = ALL)
    private CourseSettings settings;

    @OneToMany(mappedBy = "course", cascade = ALL)
    private Set<Lesson> lessons;

    @OneToMany(mappedBy = "course", cascade = ALL)
    private Set<CourseEnrollment> enrollments;

    public void setSettings(CourseSettings settings) {
        this.settings = settings;
        settings.setCourse(this);
    }

}