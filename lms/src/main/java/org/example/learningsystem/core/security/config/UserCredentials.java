package org.example.learningsystem.core.security.config;

import jakarta.validation.constraints.NotEmpty;

/**
 * Represents user credentials.
 *
 * @param username the username of the user
 * @param password the password of the user
 */
public record UserCredentials(@NotEmpty String username, @NotEmpty String password) {
}
