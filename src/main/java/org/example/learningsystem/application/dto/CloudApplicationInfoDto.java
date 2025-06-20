package org.example.learningsystem.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.learningsystem.btp.xsuaa.config.XsuaaProperties;

/**
 * DTO representing cloud-specific application information.
 * <p>
 * Contains XSUAA configuration properties.
 */
@AllArgsConstructor
@Getter
public class CloudApplicationInfoDto extends ApplicationInfoDto {

    private XsuaaProperties xsuaaProperties;
}
