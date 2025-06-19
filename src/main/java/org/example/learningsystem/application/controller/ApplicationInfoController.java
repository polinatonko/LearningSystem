package org.example.learningsystem.application.controller;

import lombok.RequiredArgsConstructor;
import org.example.learningsystem.application.service.ApplicationInfoService;
import org.example.learningsystem.application.dto.ApplicationInfoDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/application-info")
@RequiredArgsConstructor
public class ApplicationInfoController {

    private final ApplicationInfoService applicationInfoService;

    @GetMapping
    public ApplicationInfoDto getInfo() {
        return applicationInfoService.get();
    }
}
