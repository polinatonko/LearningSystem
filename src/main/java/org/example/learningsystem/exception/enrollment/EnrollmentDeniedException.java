package org.example.learningsystem.exception.enrollment;

import java.util.UUID;

public class EnrollmentDeniedException extends RuntimeException {

    public EnrollmentDeniedException(UUID id) {
        super("Enrollment to the course is not allowed [id = %s]".formatted(id));
    }
}