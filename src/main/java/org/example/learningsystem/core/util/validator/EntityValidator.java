package org.example.learningsystem.core.util.validator;

public interface EntityValidator<T> {

    void validateForInsert(T entity);

    void validateForUpdate(T entity);
}
