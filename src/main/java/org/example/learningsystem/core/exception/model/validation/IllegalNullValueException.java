package org.example.learningsystem.core.exception.model.validation;

public class IllegalNullValueException extends RuntimeException {

    public IllegalNullValueException(String property) {
        super("Null value not allowed [%s = null]".formatted(property));
    }

}
