package org.example.learningsystem.core.security.config;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCredentials {

    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
}
