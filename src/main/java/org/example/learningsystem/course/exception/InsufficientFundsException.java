package org.example.learningsystem.course.exception;

import java.math.BigDecimal;
import java.util.UUID;

public class InsufficientFundsException extends RuntimeException {

    public InsufficientFundsException(BigDecimal price, UUID courseId, UUID studentId) {
        super("Insufficient funds to purchase the course: %.2f required [courseId = %s, studentId = %s]"
                .formatted(price, courseId, studentId));
    }
}
