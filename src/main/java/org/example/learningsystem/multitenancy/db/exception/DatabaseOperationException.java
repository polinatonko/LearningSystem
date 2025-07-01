package org.example.learningsystem.multitenancy.db.exception;

import org.example.learningsystem.core.exception.model.LearningManagementSystemException;

import java.sql.SQLException;

/**
 * Exception thrown when an error occurs during a database operation.
 */
public class DatabaseOperationException extends LearningManagementSystemException {

    public DatabaseOperationException(SQLException e) {
        super("Failed to execute query", e);
    }
}
