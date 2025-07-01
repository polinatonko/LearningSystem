package org.example.learningsystem.course.exception;

import org.example.learningsystem.core.exception.model.LearningManagementSystemException;

import java.util.UUID;

public class EnrollmentDeniedException extends LearningManagementSystemException {

    public EnrollmentDeniedException(UUID id) {
        super("Enrollment to the course is not allowed [id = %s]".formatted(id));
    }
}