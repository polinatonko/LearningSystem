package org.example.learningsystem.service.course;

import lombok.RequiredArgsConstructor;
import org.example.learningsystem.domain.Enrollment;
import org.example.learningsystem.domain.EnrollmentId;
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
    public void enrollStudent(UUID courseId, UUID studentId) {
        var course = courseService.getById(courseId);
        var student = studentService.getById(studentId);
        var enrollment = new Enrollment(course, student);
        repository.save(enrollment);
    }

    @Override
    public void unenrollStudent(UUID courseId, UUID studentId) {
        var id = new EnrollmentId(courseId, studentId);
        repository.deleteById(id);
    }
}
