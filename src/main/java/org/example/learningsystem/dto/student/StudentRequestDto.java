package org.example.learningsystem.dto.student;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.example.learningsystem.utils.DataFormatUtils.DATE_FORMAT;

public record StudentRequestDto(
        UUID id,
        @Size(max = 30)
        String firstName,
        @Size(max = 30)
        String lastName,
        @NotBlank
        @Size(max = 254)
        @Email
        String email,
        @NotNull
        @JsonFormat(pattern = DATE_FORMAT)
        LocalDate dateOfBirth,
        @Min(0)
        BigDecimal coins
) {
}