package org.example.learningsystem.core.security.authority;

import lombok.AllArgsConstructor;

/**
 * Represents user authorities in the business domain.
 */
@AllArgsConstructor
public enum UserAuthority {

    /**
     * Admin authority with unrestricted access to the application.
     */
    ADMIN("Admin"),

    /**
     * Manager authority with elevated privileges for extended functions.
     */
    MANAGER("Manager"),

    /**
     * Student authority with access to basic functionality.
     */
    STUDENT("Student");

    /**
     * The human-readable name of the authority.
     */
    final String name;

    @Override
    public String toString() {
        return name;
    }
}
