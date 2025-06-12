package org.example.learningsystem.btp.saasprovisioningservice.service;

import lombok.extern.slf4j.Slf4j;
import org.example.learningsystem.btp.saasprovisioningservice.config.ServiceDependencies;
import org.example.learningsystem.btp.saasprovisioningservice.dto.ServiceInfoDto;
import org.example.learningsystem.btp.saasprovisioningservice.dto.SubscriptionRequestDto;
import org.example.learningsystem.core.multitenancy.db.service.TenantManagementService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SubscriptionServiceImpl implements SubscriptionService {

    private static final String TENANT_SPECIFIC_URL_TEMPLATE = "https://%s-%s";

    private final String appRouterUrl;
    private final ServiceDependencies serviceDependencies;
    private final TenantManagementService tenantManagementService;

    public SubscriptionServiceImpl(@Value("${approuter.url}") String appRouterUrl,
                                   ServiceDependencies serviceDependencies,
                                   TenantManagementService tenantManagementService) {
        this.appRouterUrl = extractDomain(appRouterUrl);
        this.serviceDependencies = serviceDependencies;
        this.tenantManagementService = tenantManagementService;
    }

    @Override
    public String subscribe(String tenantId, SubscriptionRequestDto subscription) {
        var tenantUrl = TENANT_SPECIFIC_URL_TEMPLATE.formatted(subscription.subscribedSubdomain(), appRouterUrl);
        log.info("Generated tenant url: tenantId = {}, tenantUrl = {}", tenantId, tenantUrl);
        tenantManagementService.create(tenantId);
        return tenantUrl;
    }

    @Override
    public void unsubscribe(String tenantId, SubscriptionRequestDto subscription) {
        tenantManagementService.delete(tenantId);
    }

    @Override
    public List<ServiceInfoDto> getDependencies() {
        return serviceDependencies.getDependencies()
                .stream()
                .map(ServiceInfoDto::new)
                .toList();
    }

    private String extractDomain(String url) {
        var domainIdx = url.indexOf('-') + 1;
        return url.substring(domainIdx);
    }
}
