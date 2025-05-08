package org.example.learningsystem.core.featureflags.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class FeatureFlagsException extends ResponseStatusException {
    public FeatureFlagsException(HttpStatusCode statusCode, String message) {
        super(statusCode,
                "Feature Flags Service returned unexpected response: %s".formatted(message));
    }
}
