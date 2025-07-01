package org.example.learningsystem.core.web.exception;

import org.example.learningsystem.core.exception.model.LearningManagementSystemException;

/**
 * Exception thrown when an invalid API response error is encountered.
 */
public class InvalidApiResponseException extends LearningManagementSystemException {

    /**
     * Constructor for the {@link InvalidApiResponseException}.
     *
     * @param errorMessage the error message
     */
    public InvalidApiResponseException(String errorMessage) {
        super(errorMessage);
    }
}
