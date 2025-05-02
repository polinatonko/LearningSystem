package org.example.learningsystem.config;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@Validated
@Getter
@Setter
public class SendingEmailProperties {
    @NotNull
    private String from;
    private String name;
    @NotNull
    private String token;
}