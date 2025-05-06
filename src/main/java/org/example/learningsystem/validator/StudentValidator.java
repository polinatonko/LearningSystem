package org.example.learningsystem.validator;

import org.example.learningsystem.domain.Student;
import org.example.learningsystem.exception.validation.InsufficientBirthDateException;
import org.springframework.stereotype.Component;

import static java.time.LocalDate.now;

@Component
public class StudentValidator implements EntityValidator<Student> {

    public void validateForInsert(Student student) {
        validateDateOfBirth(student);
    }

    public void validateForUpdate(Student student) {
        validateDateOfBirth(student);
    }

    private void validateDateOfBirth(Student student) {
        var twelveYearsBefore = now().minusYears(12);
        var dateOfBirth = student.getDateOfBirth();
        if (dateOfBirth.isAfter(twelveYearsBefore)) {
            throw new InsufficientBirthDateException(dateOfBirth);
        }
    }
}
