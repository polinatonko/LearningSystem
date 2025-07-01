package org.example.learningsystem.core.exception.model.validation;

import org.example.learningsystem.core.exception.model.EntityNotFoundException;
import org.example.learningsystem.core.exception.model.LearningManagementSystemException;

/**
 * Exception thrown when an illegal {@code null} value was encountered.
 */
public class IllegalNullValueException extends LearningManagementSystemException {

    /**
     * Constructor for the {@link EntityNotFoundException}.
     *
     * @param property the name of the property that contained the {@code null} value
     */
    public IllegalNullValueException(String property) {
        super("Null value not allowed [%s = null]".formatted(property));
    }
}
