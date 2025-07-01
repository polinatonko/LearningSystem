package org.example.learningsystem.core.template.exception;

import org.example.learningsystem.core.exception.model.LearningManagementSystemException;

/**
 * Exception thrown when a build template error is encountered.
 */
public class RenderTemplateException extends LearningManagementSystemException {

    /**
     * Constructor for the {@link RenderTemplateException}.
     *
     * @param errorMessage the error message
     */
    public RenderTemplateException(String errorMessage, Throwable throwable) {
        super(errorMessage, throwable);
    }
}
