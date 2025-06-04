package org.example.learningsystem.course.exception;

import java.util.UUID;

public class DuplicateEnrollmentException extends RuntimeException {

    public DuplicateEnrollmentException(UUID courseId, UUID studentId) {
        super("Duplicate enrollment [courseId = %s, studentId = %s]".formatted(courseId, studentId));
    }
}
