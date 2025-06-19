package org.example.learningsystem.multitenancy.subscription.controller;

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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/subscriptions")
@RequiredArgsConstructor
@Slf4j
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PutMapping("/tenants/{tenantId}")
    public String subscribe(
            @PathVariable("tenantId") String tenantId, @RequestBody SubscriptionRequestDto subscription) {
        log.info("Subscription request [tenantId = {}, subdomain = {}]", tenantId, subscription.subscribedSubdomain());
        return subscriptionService.subscribe(tenantId, subscription);
    }

    @DeleteMapping("/tenants/{tenantId}")
    public void unsubscribe(
            @PathVariable("tenantId") String tenantId, @RequestBody SubscriptionRequestDto subscription) {
        log.info("Delete subscription request [tenantId = {}, subdomain = {}]", tenantId, subscription.subscribedSubdomain());
        subscriptionService.unsubscribe(tenantId, subscription);
    }

    @GetMapping("/dependencies")
    public List<ServiceInfoDto> getDependencies(@RequestParam("tenantId") String tenantId) {
        log.info("Dependencies request [tenantId = {}]", tenantId);
        var dependencies = subscriptionService.getDependencies();
        log.info("Retrieved dependencies: {}", dependencies);
        return dependencies;
    }
}
