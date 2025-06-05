package org.example.learningsystem.btp.saapprovisioningservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.learningsystem.btp.saapprovisioningservice.dto.ApplicationInfoDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/application-info")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationInfoDto applicationInfoDto;

    @GetMapping
    public ApplicationInfoDto getApplicationInfo() {
        return applicationInfoDto;
    }
}
