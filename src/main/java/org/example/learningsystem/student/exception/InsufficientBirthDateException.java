package org.example.learningsystem.student.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.example.learningsystem.core.util.format.DataFormatUtils;

import java.time.LocalDate;

public class InsufficientBirthDateException extends RuntimeException {

    public InsufficientBirthDateException(@JsonFormat(pattern = DataFormatUtils.DATE_FORMAT) LocalDate birthDate) {
        super("The minimum age of the student is 12 years old [birthDate = %s]".formatted(birthDate));
    }
}
