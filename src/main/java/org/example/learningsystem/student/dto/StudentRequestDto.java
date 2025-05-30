package org.example.learningsystem.student.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Locale;
import java.util.UUID;

import static org.example.learningsystem.core.util.format.DataFormatUtils.DATE_FORMAT;

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
        @Schema(pattern = "YYYY/MM/dd")
        LocalDate dateOfBirth,

        @Min(0)
        BigDecimal coins,

        Locale locale
) {
}