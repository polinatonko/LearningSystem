package org.example.learningsystem.core.security.role;

public enum UserRole {

    MANAGER("Manager"),

    STUDENT("Student");

    final String name;

    UserRole(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}
