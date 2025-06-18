package org.example.learningsystem.multitenancy.subscription.service;

import org.example.learningsystem.multitenancy.subscription.dto.ServiceInfoDto;
import org.example.learningsystem.multitenancy.subscription.dto.SubscriptionRequestDto;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Profile("!cloud")
public class LocalSubscriptionService implements SubscriptionService {

    @Override
    public String subscribe(String tenantId, SubscriptionRequestDto subscription) {
        return "";
    }

    @Override
    public void unsubscribe(String tenantId, SubscriptionRequestDto subscription) {

    }

    @Override
    public List<ServiceInfoDto> getDependencies() {
        return List.of();
    }
}
