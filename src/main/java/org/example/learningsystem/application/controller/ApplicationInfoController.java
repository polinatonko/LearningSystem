package org.example.learningsystem.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.learningsystem.application.service.ApplicationInfoService;
import org.example.learningsystem.application.dto.ApplicationInfoDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/application-info")
@RequiredArgsConstructor
@Tag(name = "Application Info Controller")
public class ApplicationInfoController {

    private final ApplicationInfoService applicationInfoService;

    @GetMapping
    @Operation(summary = "Get application info")
    public ApplicationInfoDto getInfo() {
        return applicationInfoService.get();
    }
}
