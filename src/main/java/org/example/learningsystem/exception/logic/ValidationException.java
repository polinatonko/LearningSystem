package org.example.learningsystem.exception.logic;

public abstract class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }

    public static class InvalidCourseDurationException extends ValidationException {
        public InvalidCourseDurationException() {
            super("End date of course should be smaller than the start one");
        }
    }

    public static class InsufficientBirthDateException extends ValidationException {
        public InsufficientBirthDateException() {
            super("The minimum age of a student is 12 years old");
        }
    }
}
