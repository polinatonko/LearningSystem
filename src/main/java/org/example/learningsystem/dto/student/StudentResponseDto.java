package org.example.learningsystem.dto.student;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Schema
public record StudentResponseDto(
        UUID id,
        @Schema(maxLength = 30)
        String firstName,
        @Schema(maxLength = 30)
        String lastName,
        @Schema(maxLength = 254, example = "example@gmail.com")
        String email,
        LocalDate dateOfBirth,
        @Schema(minimum = "0")
        BigDecimal coins
) {
}