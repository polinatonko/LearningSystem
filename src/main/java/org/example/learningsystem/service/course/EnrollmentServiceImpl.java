package org.example.learningsystem.service.course;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.learningsystem.domain.Enrollment;
import org.example.learningsystem.domain.EnrollmentId;
import org.example.learningsystem.exception.logic.CourseEnrollmentException;
import org.example.learningsystem.repository.EnrollmentRepository;
import org.example.learningsystem.service.student.StudentService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {
    private final EnrollmentRepository repository;
    private final CourseService courseService;
    private final StudentService studentService;

    @Override
    @Transactional
    public void enrollStudent(UUID courseId, UUID studentId) {
        var course = courseService.getById(courseId);
        if (course.getSettings() == null || !course.getSettings().getIsPublic()) {
            throw new CourseEnrollmentException.EnrollmentDeniedException(course.getTitle());
        }
        var price = course.getPrice();

        var student = studentService.getById(studentId);
        if (student.getCoins().compareTo(price) < 0) {
            throw new CourseEnrollmentException.InsufficientFundsException(course.getPrice());
        }
        student.setCoins(student.getCoins().subtract(price));
        studentService.update(student);

        course.setCoinsPaid(course.getCoinsPaid().add(price));
        courseService.update(course);

        var enrollment = new Enrollment(course, student);
        repository.save(enrollment);
    }

    @Override
    public void unenrollStudent(UUID courseId, UUID studentId) {
        var id = new EnrollmentId(courseId, studentId);
        repository.deleteById(id);
    }
}
