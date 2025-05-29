package org.example.learningsystem.btp.destination.exception;

public class MissingNoArgsConstructorException extends RuntimeException {

    public MissingNoArgsConstructorException(String className) {
        super("Class object must have a no-args constructor [className = %s]".formatted(className));
    }

}
