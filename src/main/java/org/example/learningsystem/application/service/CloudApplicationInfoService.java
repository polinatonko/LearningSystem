package org.example.learningsystem.application.service;

import lombok.RequiredArgsConstructor;
import org.example.learningsystem.application.dto.CloudApplicationInfoDto;
import org.example.learningsystem.btp.xsuaa.config.XsuaaProperties;
import org.example.learningsystem.application.dto.ApplicationInfoDto;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/**
 * Cloud implementation of {@link ApplicationInfoService}.
 */
@Service
@Profile("cloud")
@RequiredArgsConstructor
public class CloudApplicationInfoService implements ApplicationInfoService {

    private final XsuaaProperties xsuaaProperties;

    @Override
    public ApplicationInfoDto get() {
        return new CloudApplicationInfoDto(xsuaaProperties);
    }
}
