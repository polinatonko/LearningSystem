package org.example.learningsystem.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.learningsystem.btp.xsuaa.config.XsuaaProperties;

@AllArgsConstructor
@Getter
public class CloudApplicationInfoDto extends ApplicationInfoDto {

    private XsuaaProperties xsuaaProperties;
}
