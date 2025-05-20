package org.example.learningsystem.course.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.learningsystem.course.model.Course;
import org.example.learningsystem.course.model.CourseEnrollment;
import org.example.learningsystem.course.model.CourseEnrollmentId;
import org.example.learningsystem.course.validator.CourseEnrollmentValidator;
import org.example.learningsystem.student.model.Student;
import org.example.learningsystem.course.repository.CourseEnrollmentRepository;
import org.example.learningsystem.student.service.StudentService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CourseEnrollmentServiceImpl implements CourseEnrollmentService {

    private final CourseService courseService;
    private final CourseEnrollmentRepository courseEnrollmentRepository;
    private final CourseEnrollmentValidator courseEnrollmentValidator;
    private final StudentService studentService;

    @Override
    @Transactional
    public void enrollStudent(UUID courseId, UUID studentId) {
        var course = courseService.getByIdForUpdate(courseId);
        var student = studentService.getByIdForUpdate(studentId);
        var enrollment = new CourseEnrollment(course, student);

        courseEnrollmentValidator.validateForInsert(enrollment);

        transferCoins(course, student);
        courseEnrollmentRepository.save(enrollment);
    }

    @Override
    public void unenrollStudent(UUID courseId, UUID studentId) {
        var id = new CourseEnrollmentId(courseId, studentId);
        courseEnrollmentRepository.deleteById(id);
    }

    private void transferCoins(Course course, Student student) {
        var coursePrice = course.getPrice();
        var studentCoins = student.getCoins();
        var coinsPaid = course.getCoinsPaid();

        student.setCoins(studentCoins.subtract(coursePrice));
        course.setCoinsPaid(coinsPaid.add(coursePrice));
    }

}
