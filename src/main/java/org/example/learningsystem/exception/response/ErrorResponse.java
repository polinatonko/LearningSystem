package org.example.learningsystem.exception.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

import static org.example.learningsystem.utils.DataFormatUtils.DATE_TIME_FORMAT;

public record ErrorResponse(
        String message,
        Integer code,
        @JsonFormat(pattern = DATE_TIME_FORMAT)
        LocalDateTime timestamp) {
    public ErrorResponse(String message, Integer code) {
        this(message, code, LocalDateTime.now());
    }
}
