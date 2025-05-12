package org.example.learningsystem.course.validator;

import org.example.learningsystem.course.exception.EnrollmentDeniedException;
import org.example.learningsystem.course.exception.InsufficientFundsException;
import org.example.learningsystem.course.model.Course;
import org.example.learningsystem.student.model.Student;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
public class CourseEnrollmentValidator {

    public void validateCourseAvailability(Course course) {
        if (isNull(course.getSettings()) || !course.getSettings().getIsPublic() || isNull(course.getPrice())) {
            throw new EnrollmentDeniedException(course.getId());
        }
    }

    public void validateStudentBalance(Course course, Student student) {
        var coursePrice = course.getPrice();
        var studentCoins = student.getCoins();
        if (studentCoins.compareTo(coursePrice) < 0) {
            throw new InsufficientFundsException(coursePrice, course.getId(), student.getId());
        }
    }
}
