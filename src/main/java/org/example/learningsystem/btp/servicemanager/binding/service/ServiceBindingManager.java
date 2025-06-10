package org.example.learningsystem.btp.servicemanager.binding.service;

import org.example.learningsystem.btp.servicemanager.binding.dto.CreateServiceBindingRequestDto;
import org.example.learningsystem.btp.servicemanager.binding.dto.ServiceBindingResponseDto;
import org.example.learningsystem.btp.servicemanager.common.service.BaseServiceManager;
import org.example.learningsystem.btp.servicemanager.common.util.ServiceManagerURIBuilder;
import org.example.learningsystem.btp.servicemanager.common.util.ServiceManagerRestClientImpl;
import org.example.learningsystem.btp.servicemanager.common.validator.ServiceManagerResponseValidator;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.example.learningsystem.btp.servicemanager.common.constant.ServiceManagerResourceConstants.NAME;
import static org.example.learningsystem.btp.servicemanager.common.constant.ServiceManagerResourceConstants.SERVICE_BINDINGS;
import static org.example.learningsystem.btp.servicemanager.common.constant.ServiceManagerResourceConstants.TENANT_ID;

@Component
@Profile("cloud")
public class ServiceBindingManager extends BaseServiceManager {

    public ServiceBindingManager(ServiceManagerResponseValidator serviceManagerResponseValidator,
                                 ServiceManagerRestClientImpl serviceManagerRestClientImpl,
                                 ServiceManagerURIBuilder serviceManagerURIBuilder) {
        super(serviceManagerResponseValidator, serviceManagerRestClientImpl, serviceManagerURIBuilder);
    }

    public ServiceBindingResponseDto create(String name, UUID serviceInstanceId, String tenantId) {
        var uri = serviceManagerURIBuilder.builder(SERVICE_BINDINGS)
                .async(false)
                .build();
        var labels = Map.of(TENANT_ID, List.of((Object) tenantId));
        var body = new CreateServiceBindingRequestDto(name, serviceInstanceId, labels);
        return serviceManagerRestClient.post(uri, body, ServiceBindingResponseDto.class);
    }

    public ServiceBindingResponseDto getByTenantId(String tenantId) {
        return getServiceByLabel(TENANT_ID, tenantId, SERVICE_BINDINGS, ServiceBindingResponseDto.class);
    }

    public void deleteByName(String name) {
        var binding = getServiceByField(NAME, name, SERVICE_BINDINGS, ServiceBindingResponseDto.class);
        deleteById(SERVICE_BINDINGS, binding.id());
    }

    public List<ServiceBindingResponseDto> getAll() {
        var uri = serviceManagerURIBuilder.builder(SERVICE_BINDINGS).build();
        var response = getAllServices(uri, ServiceBindingResponseDto.class);
        return response.items();
    }
}
