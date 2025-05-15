package org.example.learningsystem.builder;

import org.example.learningsystem.student.model.Student;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class StudentBuilder {

    private UUID id;
    private String firstName = "Name";
    private String lastName = "Last name";
    private String email = "%s@gmail.com".formatted(UUID.randomUUID());
    private LocalDate birthDate = LocalDate.now().minusYears(12);
    private BigDecimal coins = BigDecimal.ZERO;

    public StudentBuilder() {}

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

    public StudentBuilder email(String email) {
        this.email = email;
        return this;
    }

    public StudentBuilder birthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public StudentBuilder coins(BigDecimal coins) {
        this.coins = coins;
        return this;
    }

    public Student build() {
        var student = new Student(firstName, lastName, email, birthDate, coins);
        student.setId(id);
        return student;
    }
}
