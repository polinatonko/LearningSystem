package org.example.learningsystem.btp.saasprovisioningservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.learningsystem.btp.saasprovisioningservice.config.XsuaaProperties;
import org.example.learningsystem.btp.saasprovisioningservice.dto.ApplicationInfoDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/application-info")
@RequiredArgsConstructor
public class ApplicationInfoController {

    private final XsuaaProperties xsuaaProperties;

    @GetMapping
    public ApplicationInfoDto getInfo() {
        return new ApplicationInfoDto(xsuaaProperties);
    }
}
