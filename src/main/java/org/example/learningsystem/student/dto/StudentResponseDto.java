package org.example.learningsystem.student.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Locale;
import java.util.UUID;

import static org.example.learningsystem.core.util.format.DateFormatUtils.DATE_FORMAT;
import static org.example.learningsystem.core.util.format.DateFormatUtils.DATE_TIME_FORMAT;

@Schema
public record StudentResponseDto(

        UUID id,

        @Schema(maxLength = 30)
        String firstName,

        @Schema(maxLength = 30)
        String lastName,

        @Schema(maxLength = 254, example = "example@gmail.com")
        String email,

        @Schema(pattern = "YYYY/MM/dd")
        @JsonFormat(pattern = DATE_FORMAT)
        LocalDate dateOfBirth,

        @Schema(minimum = "0")
        BigDecimal coins,

        Locale locale,

        Boolean isPublic,

        @JsonFormat(pattern = DATE_TIME_FORMAT, timezone = "UTC")
        Instant created,

        String createdBy,

        @JsonFormat(pattern = DATE_TIME_FORMAT, timezone = "UTC")
        Instant lastChanged,

        String lastChangedBy) {
}