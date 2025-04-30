package org.example.learningsystem.exception;

import java.util.UUID;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String clasName, UUID id) {
        super("Entity " + clasName + "[id=" + id + "] not found");
    }
}
