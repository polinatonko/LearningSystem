package org.example.learningsystem.core.security.role;

import lombok.AllArgsConstructor;

/**
 * Represents user authorities in the business domain.
 */
@AllArgsConstructor
public enum UserAuthority {

    /**
     * Read content.
     */
    READ("Read"),

    /**
     * Write content.
     */
    WRITE("Write"),

    /**
     * Perform management operations.
     */
    MANAGE("Manage"),

    /**
     * Administrate application.
     */
    ADMINISTRATE("Administrate");

    /**
     * Permission given by this authority.
     */
    final String permission;

    @Override
    public String toString() {
        return permission;
    }
}
