package org.example.learningsystem.service.course;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.learningsystem.domain.Course;
import org.example.learningsystem.domain.Enrollment;
import org.example.learningsystem.domain.EnrollmentId;
import org.example.learningsystem.domain.Student;
import org.example.learningsystem.exception.enrollment.DuplicateEnrollmentException;
import org.example.learningsystem.exception.enrollment.EnrollmentDeniedException;
import org.example.learningsystem.exception.enrollment.InsufficientFundsException;
import org.example.learningsystem.repository.EnrollmentRepository;
import org.example.learningsystem.service.student.StudentService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class CourseEnrollmentServiceImpl implements CourseEnrollmentService {

    private final CourseService courseService;
    private final EnrollmentRepository enrollmentRepository;
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

        var enrollment = new Enrollment(course, student);
        enrollmentRepository.save(enrollment);

        studentService.update(student);
        courseService.update(course);
    }

    @Override
    public void unenrollStudent(UUID courseId, UUID studentId) {
        var id = new EnrollmentId(courseId, studentId);
        enrollmentRepository.deleteById(id);
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
        if (enrollmentRepository.existsByCourseIdAndStudentId(courseId, studentId)) {
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
