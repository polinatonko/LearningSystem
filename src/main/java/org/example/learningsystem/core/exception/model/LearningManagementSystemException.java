package org.example.learningsystem.core.exception.model;

/**
 * Represents an error occurred in the application.
 */
public class LearningManagementSystemException extends RuntimeException {

    public LearningManagementSystemException(String errorMessage) {
        super(errorMessage);
    }

    public LearningManagementSystemException(String errorMessage, Throwable throwable) {
        super(errorMessage, throwable);
    }
}
