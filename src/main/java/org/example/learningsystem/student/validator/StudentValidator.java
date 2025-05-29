package org.example.learningsystem.student.validator;

import org.example.learningsystem.student.model.Student;
import org.example.learningsystem.student.exception.InsufficientBirthDateException;
import org.example.learningsystem.core.util.validator.EntityValidator;
import org.springframework.stereotype.Component;

import static java.time.LocalDate.now;

@Component
public class StudentValidator implements EntityValidator<Student> {

    private static final int MINIMAL_AGE = 12;

    @Override
    public void validateForInsert(Student student) {
        validateDateOfBirth(student);
    }

    @Override
    public void validateForUpdate(Student student) {
        validateDateOfBirth(student);
    }

    private void validateDateOfBirth(Student student) {
        var twelveYearsBefore = now().minusYears(MINIMAL_AGE);
        var dateOfBirth = student.getDateOfBirth();
        if (dateOfBirth.isAfter(twelveYearsBefore)) {
            throw new InsufficientBirthDateException(dateOfBirth);
        }
    }

}
