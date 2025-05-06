package org.example.learningsystem.course.service;

import lombok.RequiredArgsConstructor;
import org.example.learningsystem.course.model.Course;
import org.example.learningsystem.exception.logic.EntityNotFoundException;
import org.example.learningsystem.course.repository.CourseRepository;
import org.example.learningsystem.core.util.validator.EntityValidator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static java.time.LocalDate.now;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final EntityValidator<Course> courseValidator;

    @Override
    public Course create(Course course) {
        courseValidator.validateForInsert(course);
        return courseRepository.save(course);
    }

    @Override
    public Course getById(UUID id) {
        return findById(id);
    }

    @Override
    public List<Course> getAll() {
        return courseRepository.findAll();
    }

    @Override
    public List<Course> getUpcoming(int days) {
        var nextDay = now().plusDays(1);
        var startDate = nextDay.atStartOfDay();
        var endDate = startDate.plusDays(days);
        return courseRepository.findAllBySettingsStartDateBetween(startDate, endDate);
    }

    @Override
    public Course update(Course course) {
        findById(course.getId());
        courseValidator.validateForUpdate(course);
        return courseRepository.save(course);
    }

    @Override
    public void delete(UUID id) {
        courseRepository.deleteById(id);
    }

    private Course findById(UUID id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Course.class.getName(), id));
    }
}
