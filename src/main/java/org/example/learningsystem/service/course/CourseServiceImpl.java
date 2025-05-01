package org.example.learningsystem.service.course;

import lombok.RequiredArgsConstructor;
import org.example.learningsystem.domain.Course;
import org.example.learningsystem.domain.Enrollment;
import org.example.learningsystem.domain.EnrollmentId;
import org.example.learningsystem.exception.EntityNotFoundException;
import org.example.learningsystem.repository.CourseRepository;
import org.example.learningsystem.repository.EnrollmentRepository;
import org.example.learningsystem.service.student.StudentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CourseRepository repository;
    private final EnrollmentRepository enrollmentRepository;
    private final StudentService studentService;

    @Override
    public Course create(Course course) {
        return repository.save(course);
    }

    @Override
    public Course getById(UUID id) {
        return findById(id);
    }

    @Override
    public List<Course> getAll() {
        return repository.findAll();
    }

    @Override
    public Course update(Course course) {
        findById(course.getId());
        return repository.save(course);
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    private Course findById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Course.class.getName(), id));
    }
}
