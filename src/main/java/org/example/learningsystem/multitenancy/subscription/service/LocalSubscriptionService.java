package org.example.learningsystem.multitenancy.subscription.service;

import lombok.RequiredArgsConstructor;
import org.example.learningsystem.application.config.ApplicationProperties;
import org.example.learningsystem.multitenancy.subscription.dto.ServiceInfoDto;
import org.example.learningsystem.multitenancy.subscription.dto.SubscriptionRequestDto;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Profile("!cloud")
@RequiredArgsConstructor
public class LocalSubscriptionService implements SubscriptionService {

    private final ApplicationProperties applicationProperties;

    @Override
    public String subscribe(String tenantId, SubscriptionRequestDto subscription) {
        return applicationProperties.getUri();
    }

    @Override
    public void unsubscribe(String tenantId, SubscriptionRequestDto subscription) {

    }

    @Override
    public List<ServiceInfoDto> getDependencies() {
        return List.of();
    }
}
