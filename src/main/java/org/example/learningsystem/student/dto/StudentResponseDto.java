package org.example.learningsystem.student.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.example.learningsystem.core.util.format.DataFormatUtils.DATE_FORMAT;

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

        @Schema(maxLength = 3, examples = {"en", "840"})
        String language
) {
}