package org.example.learningsystem.core.exception.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

import static org.example.learningsystem.core.util.format.DateFormatUtils.DATE_TIME_FORMAT;

/**
 * Represents a standardized error response returned by the API when an exception occurs.
 *
 * @param message   the message of the response
 * @param code      the HTTP status code
 * @param timestamp the date and time when the error occurred
 */
public record ErrorResponse(

        String message,

        Integer code,
        @JsonFormat(pattern = DATE_TIME_FORMAT)

        LocalDateTime timestamp) {

    /**
     * Constructor for the {@link ErrorResponse} with the current timestamp.
     *
     * @param message the description of the error
     * @param code    the {@code Integer} value of the HTTP status code
     */
    public ErrorResponse(String message, Integer code) {
        this(message, code, LocalDateTime.now());
    }

    /**
     * Creates a new {@link ErrorResponse} from the given parameters.
     * <p>
     * The timestamp will be automatically set to the current date and time.
     * @param message the message of the response
     * @param status  the HTTP status code of the response
     * @return a new {@link ErrorResponse}
     */
    public static ErrorResponse of(String message, HttpStatus status) {
        return new ErrorResponse(message, status.value());
    }
}
