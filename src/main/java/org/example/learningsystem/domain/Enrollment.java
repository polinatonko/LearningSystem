package org.example.learningsystem.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
