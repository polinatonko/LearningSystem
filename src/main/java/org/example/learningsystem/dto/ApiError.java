package org.example.learningsystem.dto;

import java.time.LocalDateTime;

public record ApiError(String message, Integer code, LocalDateTime timestamp) {
    public ApiError(String message, Integer code) {
        this(message, code, LocalDateTime.now());
    }

    public ApiError(String message) {
        this(message, 500);
    }
}
