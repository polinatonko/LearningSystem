package org.example.learningsystem.course.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.learningsystem.core.util.validator.EntityValidator;
import org.example.learningsystem.course.model.Course;
import org.example.learningsystem.course.model.CourseEnrollment;
import org.example.learningsystem.course.model.CourseEnrollmentId;
import org.example.learningsystem.student.model.Student;
import org.example.learningsystem.course.repository.CourseEnrollmentRepository;
import org.example.learningsystem.student.service.StudentService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CourseEnrollmentServiceImpl implements CourseEnrollmentService {

    private final CourseService courseService;
    private final CourseEnrollmentRepository courseEnrollmentRepository;
    private final EntityValidator<CourseEnrollment> courseEnrollmentValidator;
    private final StudentService studentService;

    @Override
    @Transactional
    public void enrollStudent(UUID courseId, UUID studentId) {
        var course = courseService.getByIdForUpdate(courseId);
        var student = studentService.getByIdForUpdate(studentId);
        var enrollment = new CourseEnrollment(course, student);
        var coursePrice = course.getPrice();

        courseEnrollmentValidator.validateForInsert(enrollment);

        transferCoins(course, student, coursePrice);
        courseEnrollmentRepository.save(enrollment);
    }

    @Override
    public void unenrollStudent(UUID courseId, UUID studentId) {
        var id = new CourseEnrollmentId(courseId, studentId);
        courseEnrollmentRepository.deleteById(id);
    }

    private void transferCoins(Course course, Student student, BigDecimal coins) {
        var coinsPaid = course.getCoinsPaid();
        var studentCoins = student.getCoins();

        student.setCoins(studentCoins.subtract(coins));
        course.setCoinsPaid(coinsPaid.add(coins));

        courseService.update(course);
        studentService.update(student);
    }

}
