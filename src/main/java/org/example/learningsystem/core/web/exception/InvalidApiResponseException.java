package org.example.learningsystem.core.web.exception;

/**
 * Exception thrown when an invalid API response error is encountered.
 */
public class InvalidApiResponseException extends RuntimeException {

    /**
     * Constructor for the {@link InvalidApiResponseException}.
     *
     * @param errorMessage the error message
     */
    public InvalidApiResponseException(String errorMessage) {
        super(errorMessage);
    }

}
