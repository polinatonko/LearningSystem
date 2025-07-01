package org.example.learningsystem.multitenancy.exception;

import org.example.learningsystem.core.exception.model.LearningManagementSystemException;

/**
 * Exception throws when invalid information about tenant is provided.
 */
public class InvalidTenantException extends LearningManagementSystemException {

    public InvalidTenantException(String errMessage) {
        super(errMessage);
    }
}
