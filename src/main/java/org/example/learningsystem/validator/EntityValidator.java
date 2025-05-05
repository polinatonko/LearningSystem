package org.example.learningsystem.validator;

public interface EntityValidator<T> {

    void validate(T entity);
}
