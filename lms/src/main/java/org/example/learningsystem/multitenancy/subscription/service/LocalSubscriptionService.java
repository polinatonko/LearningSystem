package org.example.learningsystem.multitenancy.subscription.service;

import lombok.RequiredArgsConstructor;
import org.example.learningsystem.application.config.ApplicationProperties;
import org.example.learningsystem.multitenancy.db.service.TenantDatabaseManagementService;
import org.example.learningsystem.multitenancy.subscription.dto.ServiceInfoDto;
import org.example.learningsystem.multitenancy.subscription.dto.SubscriptionRequestDto;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Local implementation of {@link SubscriptionService}.
 */
@Service
@Profile("!cloud")
@RequiredArgsConstructor
public class LocalSubscriptionService implements SubscriptionService {

    private final ApplicationProperties applicationProperties;
    private final TenantDatabaseManagementService tenantDatabaseManagementService;

    @Override
    public String subscribe(String tenantId, SubscriptionRequestDto subscription) {
        tenantDatabaseManagementService.createSchema(tenantId, subscription.subscribedSubdomain());
        return applicationProperties.getUri();
    }

    @Override
    public void unsubscribe(String tenantId, SubscriptionRequestDto subscription) {
        tenantDatabaseManagementService.deleteSchema(tenantId, subscription.subscribedSubdomain());
    }

    @Override
    public List<ServiceInfoDto> getDependencies() {
        return List.of();
    }
}
