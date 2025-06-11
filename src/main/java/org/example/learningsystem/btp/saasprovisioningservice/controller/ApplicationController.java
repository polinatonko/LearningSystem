package org.example.learningsystem.btp.saasprovisioningservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.learningsystem.btp.saasprovisioningservice.config.ApplicationInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/application-info")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationInfo applicationInfo;

    @GetMapping
    public ApplicationInfo getApplicationInfo() {
        return applicationInfo;
    }
}
