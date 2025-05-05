package org.example.learningsystem.validator;

import org.example.learningsystem.domain.Course;
import org.example.learningsystem.exception.validation.InvalidCourseDurationException;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Component
public class CourseValidator implements EntityValidator<Course> {

    public void validate(Course course) {
        var settings = course.getSettings();
        if (isNull(settings)) {
            return;
        }

        var startDate = settings.getStartDate();
        var endDate = settings.getEndDate();
        if (nonNull(settings.getStartDate()) && nonNull(settings.getEndDate()) && startDate.isAfter(endDate)) {
            throw new InvalidCourseDurationException(startDate, endDate);
        }
    }
}
