package org.example.learningsystem.core.security.role;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.example.learningsystem.core.security.role.UserAuthority.ADMINISTRATE;
import static org.example.learningsystem.core.security.role.UserAuthority.MANAGE;
import static org.example.learningsystem.core.security.role.UserAuthority.READ;
import static org.example.learningsystem.core.security.role.UserAuthority.WRITE;

/**
 * Represents a user role with associated permissions.
 */
@AllArgsConstructor
public enum UserRole {

    ADMIN("Admin", Set.of(READ, WRITE, MANAGE, ADMINISTRATE)),

    MANAGER("Manager", Set.of(READ, WRITE, MANAGE)),

    STUDENT("Student", Set.of(READ, WRITE));

    private final String name;
    private final Set<UserAuthority> authorities;

    public List<GrantedAuthority> getGrantedAuthorities() {
        var authorityStream = this.authorities.stream()
                .map(UserAuthority::toString)
                .map(SimpleGrantedAuthority::new);
        var roleStream = Stream.of(new SimpleGrantedAuthority(toString()));
        return Stream.concat(authorityStream, roleStream)
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "ROLE_%s".formatted(name);
    }
}
