package org.example.learningsystem.course.exception;

import org.example.learningsystem.core.exception.model.LearningManagementSystemException;

import java.util.UUID;

public class DuplicateEnrollmentException extends LearningManagementSystemException {

    public DuplicateEnrollmentException(UUID courseId, UUID studentId) {
        super("Duplicate enrollment [courseId = %s, studentId = %s]".formatted(courseId, studentId));
    }
}
