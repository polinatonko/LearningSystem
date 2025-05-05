package org.example.learningsystem.exception.enrollment;

import java.util.UUID;

public class DuplicateEnrollmentException extends RuntimeException {

    public DuplicateEnrollmentException(UUID courseId, UUID studentId) {
        super("Duplicate enrollment [courseId = %s, studentId = %s]".formatted(courseId, studentId));
    }
}
