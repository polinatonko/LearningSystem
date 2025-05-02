package org.example.learningsystem.exception.response;

import java.time.LocalDateTime;

public record ErrorResponse(String message, Integer code, LocalDateTime timestamp) {
    public ErrorResponse(String message, Integer code) {
        this(message, code, LocalDateTime.now());
    }
}
