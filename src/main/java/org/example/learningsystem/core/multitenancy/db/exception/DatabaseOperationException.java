package org.example.learningsystem.core.multitenancy.db.exception;

import java.sql.SQLException;

public class DatabaseOperationException extends RuntimeException {

    public DatabaseOperationException(SQLException e) {
        super("Failed to execute query", e);
    }
}
