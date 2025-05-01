package org.example.learningsystem.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "course_student")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString(of = "id")
@NoArgsConstructor
public class Enrollment {
    @EmbeddedId
    private EnrollmentId id;

    @ManyToOne
    @MapsId("courseId")
    private Course course;
    @ManyToOne
    @MapsId("studentId")
    private Student student;

    public Enrollment(Course course, Student student) {
        this.course = course;
        this.student = student;
        this.id = new EnrollmentId(course.getId(), student.getId());
    }
}
