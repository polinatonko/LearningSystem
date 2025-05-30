package org.example.learningsystem.lesson.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.util.UUID;

import lombok.Getter;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.example.learningsystem.core.audit.model.AuditableEntity;
import org.example.learningsystem.course.model.Course;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.UUID;
import static jakarta.persistence.InheritanceType.JOINED;

@Entity
@Table(name = "lesson")
@Inheritance(strategy = JOINED)
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(of = "id", callSuper = false)
@ToString(exclude = "course")
public class Lesson extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = UUID)
    private UUID id;

    private String title;

    private Integer duration;

    @ManyToOne(optional = false, fetch = LAZY)
    private Course course;
}