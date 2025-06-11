package org.example.learningsystem.btp.saasprovisioningservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.learningsystem.btp.saasprovisioningservice.config.XSUAAProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ApplicationController {

    private final XSUAAProperties xsuaaProperties;

    @GetMapping("/application-info")
    public XSUAAProperties getXSUAAProperties() {
        return xsuaaProperties;
    }
}
