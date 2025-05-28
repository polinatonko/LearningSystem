package org.example.learningsystem.util;

import org.example.learningsystem.student.model.Student;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class StudentTestUtils {

    public static final String FIRST_NAME = "Name";
    public static final String LAST_NAME = "Last name";
    public static final String EMAIL_TEMPLATE = "%s@gmail.com";
    public static final LocalDate DATE_OF_BIRTH = LocalDate.of(2000, 12, 1);
    public static final BigDecimal COINS = BigDecimal.ZERO;
    public static final LocalDateTime CREATED = LocalDateTime.now();
    public static final LocalDateTime LAST_CHANGED = LocalDateTime.now();

    public static Student createStudent() {
        return Student.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .email(EMAIL_TEMPLATE.formatted(UUID.randomUUID()))
                .dateOfBirth(DATE_OF_BIRTH)
                .coins(COINS)
                .build();
    }

    public static Student createSavedStudent() {
        return Student.builder()
                .id(UUID.randomUUID())
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .email(EMAIL_TEMPLATE.formatted(UUID.randomUUID()))
                .dateOfBirth(DATE_OF_BIRTH)
                .coins(COINS)
                .created(CREATED)
                .lastChanged(LAST_CHANGED)
                .build();
    }

}
