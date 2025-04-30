package org.example.learningsystem.exception;

import java.util.UUID;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String className, UUID id) {
        super(String.format("Entity %s[id=%s] not found", className, id));
    }
}
