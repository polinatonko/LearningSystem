package org.example.learningsystem.core.util.validator;

/**
 * Generic interface for validating entities before database operations.
 *
 * @param <T> the type of the entity to validate
 */
public interface EntityValidator<T> {

    /**
     * Validates the entity before insertion into the database.
     *
     * @param entity to validate
     */
    void validateForInsert(T entity);

    /**
     * Validates the entity before updating in the database.
     *
     * @param entity the entity to validate
     */
    void validateForUpdate(T entity);

    /**
     * Validates that provided entity has expected runtime type.
     *
     * @param expected the entity provided the expected type
     * @param provided the entity to check against the expected type
     */
    default void validateTypes(T expected, T provided) {
        var expectedClass = expected.getClass();
        var providedClass = provided.getClass();

        if (!expectedClass.equals(providedClass)) {
            throw new IllegalArgumentException(
                    "Illegal type provided [expected = %s, provided = %s]".formatted(
                            expectedClass.getName(), providedClass.getName())
            );
        }
    }
}
