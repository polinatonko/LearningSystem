package org.example.learningsystem.exception.logic;

import java.math.BigDecimal;

public abstract class CourseEnrollmentException extends RuntimeException {
    public CourseEnrollmentException(String message) {
        super(message);
    }

    public static class InsufficientFundsException extends CourseEnrollmentException {
        public InsufficientFundsException(BigDecimal price) {
            super(String.format("Insufficient funds to purchase the course: %.2f required", price));
        }
    }

    public static class EnrollmentDeniedException extends CourseEnrollmentException {
        public EnrollmentDeniedException(String title) {
            super(String.format("Enrollment to the course '%s' is not allowed", title));
        }
    }
}
