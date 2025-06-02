package org.example.learningsystem.core.security.role;

import lombok.AllArgsConstructor;

/**
 * Represents user roles in the business domain.
 */
@AllArgsConstructor
public enum UserRole {

    /**
     * Manager role with elevated privileges for extended functions.
     */
    MANAGER("Manager"),

    /**
     * Student role with access to basic functionality.
     */
    STUDENT("Student");

    /**
     * The human-readable name of the role.
     */
    final String name;

    @Override
    public String toString() {
        return name;
    }

}
