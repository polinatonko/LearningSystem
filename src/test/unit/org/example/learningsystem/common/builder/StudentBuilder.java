package org.example.learningsystem.common.builder;

import org.example.learningsystem.student.model.Student;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public class StudentBuilder {

    private static final String EMAIL_TEMPLATE = "%s@gmail.com";

    private BigDecimal coins = BigDecimal.ZERO;
    private LocalDate dateOfBirth = LocalDate.of(2000, 12, 1);
    private String firstName = "Name";
    private UUID id;
    private String lastName = "Last name";

    private final Instant created = Instant.now();
    private final Instant lastChanged = Instant.now();

    public StudentBuilder id(UUID id) {
        this.id = id;
        return this;
    }

    public StudentBuilder firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public StudentBuilder lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public StudentBuilder dateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public StudentBuilder coins(BigDecimal coins) {
        this.coins = coins;
        return this;
    }

    public Student build() {
        return Student.builder()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .email(EMAIL_TEMPLATE.formatted(UUID.randomUUID()))
                .coins(coins)
                .dateOfBirth(dateOfBirth)
                .created(created)
                .lastChanged(lastChanged)
                .build();
    }

}
