package org.example.learningsystem.util;

import org.example.learningsystem.student.dto.StudentRequestDto;
import org.example.learningsystem.student.model.Student;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class StudentTestUtils {

    private static final String FIRST_NAME = "Name";
    private static final String LAST_NAME = "Last name";
    private static final String EMAIL_TEMPLATE = "%s@gmail.com";
    private static final LocalDate DATE_OF_BIRTH = LocalDate.of(2000, 12, 1);
    private static final BigDecimal COINS = BigDecimal.ZERO;
    private static final String LANGUAGE = "en";

    public static Student buildStudent() {
        return Student.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .email(EMAIL_TEMPLATE.formatted(UUID.randomUUID()))
                .dateOfBirth(DATE_OF_BIRTH)
                .coins(COINS)
                .language(LANGUAGE)
                .build();
    }

    public static StudentRequestDto buildCreateStudentRequestDto() {
        return new StudentRequestDto(null,
                FIRST_NAME,
                LAST_NAME,
                EMAIL_TEMPLATE.formatted(UUID.randomUUID()),
                DATE_OF_BIRTH,
                COINS,
                LANGUAGE);
    }

    public static StudentRequestDto buildUpdateStudentRequestDto(UUID id) {
        return new StudentRequestDto(
                id,
                FIRST_NAME,
                LAST_NAME,
                EMAIL_TEMPLATE.formatted(UUID.randomUUID()),
                DATE_OF_BIRTH,
                COINS,
                LANGUAGE);
    }

}
