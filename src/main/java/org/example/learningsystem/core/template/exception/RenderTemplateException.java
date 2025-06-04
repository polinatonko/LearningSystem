package org.example.learningsystem.core.template.exception;

/**
 * Exception thrown when a render template error is encountered.
 */
public class RenderTemplateException extends RuntimeException {

    /**
     * Constructor for the {@link RenderTemplateException}.
     *
     * @param errorMessage the error message
     */
    public RenderTemplateException(String errorMessage) {
        super(errorMessage);
    }
}
