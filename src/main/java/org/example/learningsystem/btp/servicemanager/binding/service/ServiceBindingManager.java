package org.example.learningsystem.btp.servicemanager.binding.service;

import lombok.RequiredArgsConstructor;
import org.example.learningsystem.btp.servicemanager.binding.dto.CreateServiceBindingRequestDto;
import org.example.learningsystem.btp.servicemanager.binding.dto.ServiceBindingResponseDto;
import org.example.learningsystem.btp.servicemanager.common.service.BaseServiceManager;
import org.example.learningsystem.btp.servicemanager.common.util.ServiceManagerRestClient;
import org.example.learningsystem.btp.servicemanager.common.builder.ServiceManagerUriBuilder;
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

/**
 * Service for managing service bindings in SAP BTP using the Service Manager API.
 */
@Service
@Profile("cloud")
@RequiredArgsConstructor
public class ServiceBindingManager {

    private final BaseServiceManager baseServiceManager;
    private final ServiceManagerRestClient serviceManagerRestClient;
    private final ServiceManagerUriBuilder serviceManagerURIBuilder;

    /**
     * Creates a new service binding.
     *
     * @param name              the name of the binding
     * @param serviceInstanceId the id of the service instance to bind to
     * @param tenantInfo        the tenant information
     * @return the {@link ServiceBindingResponseDto} instance
     */
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

    /**
     * Retrieves a service binding by its associated tenant id.
     *
     * @param tenantId the tenant id to search for
     * @return a {@link ServiceBindingResponseDto} instance
     */
    public ServiceBindingResponseDto getByTenantId(String tenantId) {
        return baseServiceManager.getByLabel(TENANT_ID, tenantId, SERVICE_BINDINGS, ServiceBindingResponseDto.class);
    }

    /**
     * Deletes a service binding by its name.
     *
     * @param name the name of the binding.
     */
    public void deleteByName(String name) {
        var binding = baseServiceManager.getByField(NAME, name, SERVICE_BINDINGS, ServiceBindingResponseDto.class);
        baseServiceManager.deleteById(SERVICE_BINDINGS, binding.id());
    }

    /**
     * Retrieves all service bindings.
     *
     * @return a {@link List} of all service bindings
     */
    public List<ServiceBindingResponseDto> getAll() {
        var uri = serviceManagerURIBuilder.builder(SERVICE_BINDINGS).build();
        var response = baseServiceManager.getAll(uri, ServiceBindingResponseDto.class);
        return response.items();
    }
}
