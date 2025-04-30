package org.example.learningsystem.dto.student;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record StudentResponse(
        @NotNull
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
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
        LocalDate dateOfBirth,
        @NotNull
        @Min(0)
        BigDecimal coins
) {}