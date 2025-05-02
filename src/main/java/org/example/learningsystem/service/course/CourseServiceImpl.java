package org.example.learningsystem.service.course;

import lombok.RequiredArgsConstructor;
import org.example.learningsystem.domain.Course;
import org.example.learningsystem.exception.logic.EntityNotFoundException;
import org.example.learningsystem.exception.logic.ValidationException;
import org.example.learningsystem.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CourseRepository repository;

    @Override
    public Course create(Course course) {
        validate(course);
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
    public List<Course> getUpcoming(int days) {
        var startDate = LocalDate.now().atStartOfDay();
        var endDate = startDate.plusDays(days);
        return repository.findBySettingsStartDateBetween(startDate, endDate);
    }

    @Override
    public Course update(Course course) {
        findById(course.getId());
        validate(course);
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

    private void validate(Course course) {
        var settings = course.getSettings();
        if (settings == null || settings.getStartDate() == null || settings.getEndDate() == null) {
            return;
        }
        if (settings.getStartDate().isAfter(settings.getEndDate())) {
            throw new ValidationException.InvalidCourseDurationException();
        }
    }
}
