package org.example.learningsystem.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString(exclude = {"settings", "lessons", "students"})
public class Course {
    @Id
    private UUID id;
    private String title;
    private String description;
    private BigDecimal price = BigDecimal.ZERO;
    private BigDecimal coinsPaid = BigDecimal.ZERO;
    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "id")
    private CourseSettings settings;
    @OneToMany(mappedBy = "course")
    private Set<Lesson> lessons;
    @ManyToMany(mappedBy = "courses")
    private Set<Student> students;

    public void addStudent(Student student) {
        students.add(student);
        student.getCourses().add(this);
    }

    public void removeStudent(Student student) {
        students.remove(student);
        student.getCourses().remove(this);
    }
}