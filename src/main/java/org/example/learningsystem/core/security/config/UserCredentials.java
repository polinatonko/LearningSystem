package org.example.learningsystem.core.security.config;

import jakarta.validation.constraints.NotEmpty;

public record UserCredentials(@NotEmpty String username, @NotEmpty String password) {
}
