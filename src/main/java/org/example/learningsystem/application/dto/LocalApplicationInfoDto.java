package org.example.learningsystem.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.learningsystem.application.model.ApplicationProperties;

/**
 * DTO representing local application information.
 */
@AllArgsConstructor
@Getter
public class LocalApplicationInfoDto extends ApplicationInfoDto {

    private ApplicationProperties applicationProperties;
}
