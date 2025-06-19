package org.example.learningsystem.multitenancy.db.exception;

import java.sql.SQLException;

/**
 * Exception thrown when an error occurs during a database operation.
 */
public class DatabaseOperationException extends RuntimeException {

    public DatabaseOperationException(SQLException e) {
        super("Failed to execute query", e);
    }
}
