package org.example.learningsystem.student.exception;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

import static org.example.learningsystem.core.util.format.DataFormatUtils.DATE_FORMAT;

public class InsufficientBirthDateException extends RuntimeException {

    public InsufficientBirthDateException(@JsonFormat(pattern = DATE_FORMAT) LocalDate birthDate) {
        super("The minimum age of the student is 12 years old [birthDate = %s]".formatted(birthDate));
    }
}
