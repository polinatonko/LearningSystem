package org.example.learningsystem.core.exception.model;

import java.util.UUID;

/**
 * Exception thrown when a requested entity was not found in the data source.
 */
public class EntityNotFoundException extends RuntimeException {

    /**
     * Constructor for the {@link EntityNotFoundException}.
     *
     * @param className the class name of the entity that wasn't found
     * @param id        the identifier that was searched for
     */
    public EntityNotFoundException(String className, UUID id) {
        super("Entity %s not found [id = %s]".formatted(className, id));
    }
}
