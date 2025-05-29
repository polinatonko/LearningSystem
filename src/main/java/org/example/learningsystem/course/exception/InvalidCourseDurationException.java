package org.example.learningsystem.course.exception;

import java.time.LocalDateTime;

public class InvalidCourseDurationException extends RuntimeException {

    public InvalidCourseDurationException(LocalDateTime startDate, LocalDateTime endDate) {
        super("End date of the course should be smaller than the start one [startDate = %s, endDate = %s]"
                .formatted(startDate, endDate));
    }

}