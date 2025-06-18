package org.example.learningsystem.application.service;

import lombok.RequiredArgsConstructor;
import org.example.learningsystem.btp.xsuaa.config.XsuaaProperties;
import org.example.learningsystem.application.dto.ApplicationInfoDto;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicationInfoServiceImpl implements ApplicationInfoService {

    private final XsuaaProperties xsuaaProperties;

    @Override
    public ApplicationInfoDto get() {
        return new ApplicationInfoDto(xsuaaProperties);
    }
}
