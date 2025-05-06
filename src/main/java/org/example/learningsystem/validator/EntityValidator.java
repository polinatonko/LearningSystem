package org.example.learningsystem.validator;

public interface EntityValidator<T> {

    void validateForInsert(T entity);

    void validateForUpdate(T entity);
}
