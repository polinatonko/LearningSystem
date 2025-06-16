package org.example.learningsystem.btp.servicemanager.binding.service;

import lombok.RequiredArgsConstructor;
import org.example.learningsystem.btp.servicemanager.binding.dto.CreateServiceBindingRequestDto;
import org.example.learningsystem.btp.servicemanager.binding.dto.ServiceBindingResponseDto;
import org.example.learningsystem.btp.servicemanager.common.service.BaseServiceManager;
import org.example.learningsystem.btp.servicemanager.common.util.ServiceManagerRestClient;
import org.example.learningsystem.btp.servicemanager.common.util.ServiceManagerURIBuilder;
import org.example.learningsystem.multitenancy.context.TenantInfo;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.example.learningsystem.btp.servicemanager.common.constant.ServiceManagerResourceConstants.NAME;
import static org.example.learningsystem.btp.servicemanager.common.constant.ServiceManagerResourceConstants.SERVICE_BINDINGS;
import static org.example.learningsystem.btp.servicemanager.common.constant.ServiceManagerResourceConstants.SUBDOMAIN;
import static org.example.learningsystem.btp.servicemanager.common.constant.ServiceManagerResourceConstants.TENANT_ID;

@Service
@Profile("cloud")
@RequiredArgsConstructor
public class ServiceBindingManager {

    private final BaseServiceManager baseServiceManager;
    private final ServiceManagerRestClient serviceManagerRestClient;
    private final ServiceManagerURIBuilder serviceManagerURIBuilder;

    public ServiceBindingResponseDto create(String name, UUID serviceInstanceId, TenantInfo tenantInfo) {
        var uri = serviceManagerURIBuilder.builder(SERVICE_BINDINGS)
                .async(false)
                .build();
        var labels = Map.of(
                TENANT_ID, List.of((Object) tenantInfo.tenantId()),
                SUBDOMAIN, List.of((Object) tenantInfo.subdomain())
        );
        var body = new CreateServiceBindingRequestDto(name, serviceInstanceId, labels);
        return serviceManagerRestClient.post(uri, body, ServiceBindingResponseDto.class);
    }

    public ServiceBindingResponseDto getByTenantId(String tenantId) {
        return baseServiceManager.getByLabel(TENANT_ID, tenantId, SERVICE_BINDINGS, ServiceBindingResponseDto.class);
    }

    public void deleteByName(String name) {
        var binding = baseServiceManager.getByField(NAME, name, SERVICE_BINDINGS, ServiceBindingResponseDto.class);
        baseServiceManager.deleteById(SERVICE_BINDINGS, binding.id());
    }

    public List<ServiceBindingResponseDto> getAll() {
        var uri = serviceManagerURIBuilder.builder(SERVICE_BINDINGS).build();
        var response = baseServiceManager.getAll(uri, ServiceBindingResponseDto.class);
        return response.items();
    }
}
