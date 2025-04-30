package org.example.learningsystem.dto.student;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record StudentRequest(
        UUID id,
        String firstName,
        String lastName,
        String email,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
        LocalDate dateOfBirth,
        BigDecimal coins
) {}