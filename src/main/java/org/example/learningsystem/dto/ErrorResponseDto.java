package org.example.learningsystem.dto;

import java.time.LocalDateTime;

public record ErrorResponseDto(String message, Integer code, LocalDateTime timestamp) {
    public ErrorResponseDto(String message, Integer code) {
        this(message, code, LocalDateTime.now());
    }

    public ErrorResponseDto(String message) {
        this(message, 500);
    }
}
