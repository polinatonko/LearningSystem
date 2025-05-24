package org.example.learningsystem.core.security.role;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum UserRole {

    MANAGER("Manager"),

    STUDENT("Student");

    final String name;

    @Override
    public String toString() {
        return name;
    }

}
