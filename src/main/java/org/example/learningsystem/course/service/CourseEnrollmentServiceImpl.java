package org.example.learningsystem.course.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.learningsystem.course.model.Course;
import org.example.learningsystem.course.model.CourseEnrollment;
import org.example.learningsystem.course.model.CourseEnrollmentId;
import org.example.learningsystem.student.model.Student;
import org.example.learningsystem.course.exception.DuplicateEnrollmentException;
import org.example.learningsystem.course.exception.EnrollmentDeniedException;
import org.example.learningsystem.course.exception.InsufficientFundsException;
import org.example.learningsystem.course.repository.CourseEnrollmentRepository;
import org.example.learningsystem.student.service.StudentService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class CourseEnrollmentServiceImpl implements CourseEnrollmentService {

    private final CourseService courseService;
    private final CourseEnrollmentRepository courseEnrollmentRepository;
    private final StudentService studentService;

    @Override
    @Transactional
    public void enrollStudent(UUID courseId, UUID studentId) {
        var course = courseService.getById(courseId);
        var student = studentService.getById(studentId);

        validateCourseAvailability(course);
        validateStudentBalance(course, student);
        checkDuplicateEnrollment(courseId, studentId);

        transferCoins(course, student);

        var enrollment = new CourseEnrollment(course, student);
        courseEnrollmentRepository.save(enrollment);
    }

    @Override
    public void unenrollStudent(UUID courseId, UUID studentId) {
        var id = new CourseEnrollmentId(courseId, studentId);
        courseEnrollmentRepository.deleteById(id);
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

    private void checkDuplicateEnrollment(UUID courseId, UUID studentId) {
        if (courseEnrollmentRepository.existsByCourseIdAndStudentId(courseId, studentId)) {
            throw new DuplicateEnrollmentException(courseId, studentId);
        }
    }

    private void transferCoins(Course course, Student student) {
        BigDecimal coursePrice = course.getPrice();
        BigDecimal studentCoins = student.getCoins();
        BigDecimal coinsPaid = course.getCoinsPaid();

        student.setCoins(studentCoins.subtract(coursePrice));
        course.setCoinsPaid(coinsPaid.add(studentCoins));
    }
}
