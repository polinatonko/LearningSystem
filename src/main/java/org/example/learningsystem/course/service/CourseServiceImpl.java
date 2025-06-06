package org.example.learningsystem.course.service;

import lombok.RequiredArgsConstructor;
import org.example.learningsystem.course.model.Course;
import org.example.learningsystem.core.exception.model.EntityNotFoundException;
import org.example.learningsystem.course.repository.CourseRepository;
import org.example.learningsystem.core.util.validator.EntityValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static java.time.LocalDate.now;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private static final int DAYS_BEFORE = 1;

    private final CourseRepository courseRepository;
    private final EntityValidator<Course> courseValidator;

    @Override
    public Course create(Course course) {
        courseValidator.validateForInsert(course);
        return courseRepository.save(course);
    }

    @Override
    public Course getById(UUID id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Course.class.getName(), id));
    }

    @Override
    public Course getByIdForUpdate(UUID id) {
        return courseRepository.findByIdForUpdate(id)
                .orElseThrow(() -> new EntityNotFoundException(Course.class.getName(), id));
    }

    @Override
    public Page<Course> getAll(Pageable pageable) {
        return courseRepository.findAll(pageable);
    }

    @Override
    public List<Course> getUpcoming(int days) {
        var nextDay = now().plusDays(DAYS_BEFORE);
        var startDate = nextDay.atStartOfDay();
        var endDate = startDate.plusDays(days);
        return courseRepository.findAllBySettingsStartDateBetween(startDate, endDate);
    }

    @Override
    public Course update(Course course) {
        getById(course.getId());
        courseValidator.validateForUpdate(course);
        return courseRepository.save(course);
    }

    @Override
    public void deleteById(UUID id) {
        courseRepository.deleteById(id);
    }
}
