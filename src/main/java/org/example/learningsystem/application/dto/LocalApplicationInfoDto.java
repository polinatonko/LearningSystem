package org.example.learningsystem.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.learningsystem.application.config.ApplicationProperties;

@AllArgsConstructor
@Getter
public class LocalApplicationInfoDto extends ApplicationInfoDto {

    private ApplicationProperties applicationProperties;
}
