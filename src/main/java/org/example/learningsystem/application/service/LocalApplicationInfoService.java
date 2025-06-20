package org.example.learningsystem.application.service;

import lombok.RequiredArgsConstructor;
import org.example.learningsystem.application.config.ApplicationProperties;
import org.example.learningsystem.application.dto.ApplicationInfoDto;
import org.example.learningsystem.application.dto.LocalApplicationInfoDto;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/**
 * Local implementation of {@link ApplicationInfoService}.
 */
@Service
@Profile("!cloud")
@RequiredArgsConstructor
public class LocalApplicationInfoService implements ApplicationInfoService {

    private final ApplicationProperties applicationProperties;

    @Override
    public ApplicationInfoDto get() {
        return new LocalApplicationInfoDto(applicationProperties);
    }
}
