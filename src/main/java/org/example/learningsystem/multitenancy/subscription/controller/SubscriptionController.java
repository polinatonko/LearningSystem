package org.example.learningsystem.multitenancy.subscription.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.learningsystem.multitenancy.subscription.dto.ServiceInfoDto;
import org.example.learningsystem.multitenancy.subscription.dto.SubscriptionRequestDto;
import org.example.learningsystem.multitenancy.subscription.service.SubscriptionService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/api/v1/subscriptions")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Subscription Controller")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PutMapping("/tenants/{tenantId}")
    @Operation(summary = "Subscribe to application")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Course was created"),
            @ApiResponse(responseCode = "400", description = "Invalid body")
    })
    public String subscribe(
            @PathVariable("tenantId") String tenantId, @RequestBody SubscriptionRequestDto subscription) {
        log.info("Subscription request [tenantId = {}, subdomain = {}]", tenantId, subscription.subscribedSubdomain());
        return subscriptionService.subscribe(tenantId, subscription);
    }

    @DeleteMapping("/tenants/{tenantId}")
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Unsubscribe from application")
    @ApiResponse(responseCode = "204", description = "Unsubscription was successful")
    public void unsubscribe(
            @PathVariable("tenantId") String tenantId, @RequestBody SubscriptionRequestDto subscription) {
        log.info("Delete subscription request [tenantId = {}, subdomain = {}]", tenantId, subscription.subscribedSubdomain());
        subscriptionService.unsubscribe(tenantId, subscription);
    }

    @GetMapping("/dependencies")
    @Operation(summary = "Get application dependencies")
    public List<ServiceInfoDto> getDependencies(@RequestParam("tenantId") String tenantId) {
        log.info("Dependencies request [tenantId = {}]", tenantId);
        var dependencies = subscriptionService.getDependencies();
        log.info("Retrieved dependencies: {}", dependencies);
        return dependencies;
    }
}
