package org.example.learningsystem.core.exception.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

import static org.example.learningsystem.core.util.format.DataFormatUtils.DATE_TIME_FORMAT;

public record ErrorResponse(

        String message,

        Integer code,
        @JsonFormat(pattern = DATE_TIME_FORMAT)

        LocalDateTime timestamp) {

    public ErrorResponse(String message, Integer code) {
        this(message, code, LocalDateTime.now());
    }

    public static ErrorResponse of(String message, HttpStatus status) {
        return new ErrorResponse(message, status.value());
    }

}
