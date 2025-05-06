package org.example.learningsystem.util.validator;

public interface EntityValidator<T> {

    void validateForInsert(T entity);

    void validateForUpdate(T entity);
}
