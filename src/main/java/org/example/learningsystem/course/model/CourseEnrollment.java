package org.example.learningsystem.course.model;

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
import org.example.learningsystem.student.model.Student;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table(name = "course_student")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString(of = "id")
@NoArgsConstructor
public class CourseEnrollment {

    @EmbeddedId
    private CourseEnrollmentId id;

    @ManyToOne(fetch = LAZY)
    @MapsId("courseId")
    private Course course;
    @ManyToOne(fetch = LAZY)
    @MapsId("studentId")
    private Student student;

    public CourseEnrollment(Course course, Student student) {
        this.course = course;
        this.student = student;
        this.id = new CourseEnrollmentId(course.getId(), student.getId());
    }
}
