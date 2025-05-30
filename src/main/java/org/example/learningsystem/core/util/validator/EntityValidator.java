package org.example.learningsystem.core.util.validator;

public interface EntityValidator<T> {

    void validateForInsert(T entity);

    void validateForUpdate(T entity);

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
