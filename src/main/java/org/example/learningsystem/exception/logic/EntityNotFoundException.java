package org.example.learningsystem.exception.logic;

import java.util.UUID;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String className, UUID id) {
        super("Entity %s not found [id = %s]".formatted(className, id));
    }

}
