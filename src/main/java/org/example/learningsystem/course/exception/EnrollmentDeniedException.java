package org.example.learningsystem.course.exception;

import java.util.UUID;

public class EnrollmentDeniedException extends RuntimeException {

    public EnrollmentDeniedException(UUID id) {
        super("Enrollment to the course is not allowed [id = %s]".formatted(id));
    }

}