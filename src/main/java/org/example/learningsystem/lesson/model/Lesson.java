package org.example.learningsystem.lesson.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.util.UUID;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.example.learningsystem.course.model.Course;

import static jakarta.persistence.GenerationType.UUID;
import static jakarta.persistence.InheritanceType.JOINED;

@Entity
@Table(name = "lesson")
@Inheritance(strategy = JOINED)
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString(exclude = "course")
public class Lesson {

    @Id
    @GeneratedValue(strategy = UUID)
    private UUID id;
    private String title;
    private Integer duration;
    @ManyToOne(optional = false)
    private Course course;
}