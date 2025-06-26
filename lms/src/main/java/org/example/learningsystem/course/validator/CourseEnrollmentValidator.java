package org.example.learningsystem.course.validator;

import lombok.RequiredArgsConstructor;
import org.example.learningsystem.core.util.validator.EntityValidator;
import org.example.learningsystem.course.exception.DuplicateEnrollmentException;
import org.example.learningsystem.course.exception.EnrollmentDeniedException;
import org.example.learningsystem.course.exception.InsufficientFundsException;
import org.example.learningsystem.course.model.Course;
import org.example.learningsystem.course.model.CourseEnrollment;
import org.example.learningsystem.course.repository.CourseEnrollmentRepository;
import org.example.learningsystem.student.model.Student;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
public class CourseEnrollmentValidator implements EntityValidator<CourseEnrollment> {

    private final CourseEnrollmentRepository courseEnrollmentRepository;

    @Override
    public void validateForInsert(CourseEnrollment enrollment) {
        validate(enrollment);
    }

    @Override
    public void validateForUpdate(CourseEnrollment enrollment) {
        validate(enrollment);
    }

    private void validate(CourseEnrollment enrollment) {
        var course = enrollment.getCourse();
        var student = enrollment.getStudent();

        validateCourseAvailability(course);
        validateStudentBalance(course, student);
        checkDuplicateEnrollment(course, student);
    }

    private void validateCourseAvailability(Course course) {
        if (isNull(course.getSettings()) || !course.getSettings().getIsPublic() || isNull(course.getPrice())) {
            throw new EnrollmentDeniedException(course.getId());
        }
    }

    private void validateStudentBalance(Course course, Student student) {
        var coursePrice = course.getPrice();
        var studentCoins = student.getCoins();

        if (studentCoins.compareTo(coursePrice) < 0) {
            throw new InsufficientFundsException(coursePrice, course.getId(), student.getId());
        }
    }

    private void checkDuplicateEnrollment(Course course, Student student) {
        var courseId = course.getId();
        var studentId = student.getId();

        if (courseEnrollmentRepository.existsByCourseIdAndStudentId(courseId, studentId)) {
            throw new DuplicateEnrollmentException(courseId, studentId);
        }
    }
}
