package org.example.learningsystem.service.course;

import lombok.RequiredArgsConstructor;
import org.example.learningsystem.domain.Course;
import org.example.learningsystem.exception.EntityNotFoundException;
import org.example.learningsystem.repository.CourseRepository;
import org.example.learningsystem.service.student.StudentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final StudentService studentService;

    @Override
    public Course create(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public void enrollStudent(UUID studentId, UUID courseId) {
        var course = findByIdOrThrow(courseId);
        var student = studentService.getById(studentId);
        course.addStudent(student);
        courseRepository.save(course);
    }

    @Override
    public Course getById(UUID id) {
        return findByIdOrThrow(id);
    }

    @Override
    public List<Course> getAll() {
        return courseRepository.findAll();
    }

    @Override
    public Course update(Course course) {
        findByIdOrThrow(course.getId());
        return courseRepository.save(course);
    }

    @Override
    public void delete(UUID id) {
        courseRepository.deleteById(id);
    }

    @Override
    public void unenrollStudent(UUID courseId, UUID studentId) {
        var course = findByIdOrThrow(courseId);
        var student = studentService.getById(studentId);
        course.removeStudent(student);
        courseRepository.save(course);
    }

    private Course findByIdOrThrow(UUID id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Course.class.getName(), id));
    }
}
