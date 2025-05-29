package org.example.learningsystem.btp.destination.exception;

public class MissingPropertyException extends RuntimeException {

    public MissingPropertyException(String className, String name) {
        super("Missing property [class = %s, property = %s]".formatted(className, name));
    }

}
