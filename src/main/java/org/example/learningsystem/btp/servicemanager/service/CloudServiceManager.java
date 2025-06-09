package org.example.learningsystem.btp.servicemanager.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.learningsystem.btp.servicemanager.dto.CreateServiceBindingRequestDto;
import org.example.learningsystem.btp.servicemanager.dto.PaginatedServiceResponseDto;
import org.example.learningsystem.btp.servicemanager.dto.ServiceBindingResponseDto;
import org.example.learningsystem.btp.servicemanager.dto.CreateServiceInstanceByOfferingAndPlanName;
import org.example.learningsystem.btp.servicemanager.dto.ServiceInstanceResponseDto;
import org.example.learningsystem.btp.servicemanager.exception.ServiceNotFoundException;
import org.example.learningsystem.btp.servicemanager.util.ServiceManagerRestClient;
import org.example.learningsystem.btp.servicemanager.util.ServiceManagerURIBuilder;
import org.springframework.context.annotation.Profile;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.ResolvableType;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

import static java.util.Objects.isNull;

@Profile("cloud")
@Service
@Slf4j
@RequiredArgsConstructor
public class CloudServiceManager implements ServiceManager {

    private static final String FIELD_EQUALS_QUERY = "%s eq '%s'";
    private static final String SERVICE_INSTANCES_PATH_SEGMENT = "service_instances";
    private static final String SERVICE_BINDINGS_PATH_SEGMENT = "service_bindings";

    private final ServiceManagerRestClient serviceManagerRestClient;
    private final ServiceManagerURIBuilder serviceManagerURIBuilder;

    @Override
    public ServiceInstanceResponseDto createServiceInstanceByOfferingAndPlanName(String name, String offering, String servicePlan) {
        var params = Map.of("async", "false");
        var uri = serviceManagerURIBuilder.build(SERVICE_INSTANCES_PATH_SEGMENT, params);
        var body = new CreateServiceInstanceByOfferingAndPlanName(name, offering, servicePlan);
        return serviceManagerRestClient.post(uri, body, ServiceInstanceResponseDto.class);
    }

    @Override
    public ServiceBindingResponseDto createServiceBinding(String name, UUID serviceInstanceId) {
        var params = Map.of("async", "false");
        var uri = serviceManagerURIBuilder.build(SERVICE_BINDINGS_PATH_SEGMENT, params);
        var body = new CreateServiceBindingRequestDto(name, serviceInstanceId);
        return serviceManagerRestClient.post(uri, body, ServiceBindingResponseDto.class);

    }

    @Override
    public void deleteServiceInstanceByName(String name) {
        var instance = getServiceByName(name, SERVICE_INSTANCES_PATH_SEGMENT, ServiceInstanceResponseDto.class);
        deleteById(SERVICE_INSTANCES_PATH_SEGMENT, instance.id());
    }

    @Override
    public void deleteServiceBindingByName(String name) {
        var binding = getServiceByName(name, SERVICE_BINDINGS_PATH_SEGMENT, ServiceBindingResponseDto.class);
        deleteById(SERVICE_BINDINGS_PATH_SEGMENT, binding.id());
    }

    private <T> T getServiceByName(String name, String path, Class<T> serviceResponseType) {
        var fieldQuery = FIELD_EQUALS_QUERY.formatted("name", name);
        log.info("Field query: {}", fieldQuery);
        var params = Map.of("fieldQuery", fieldQuery);
        var uri = serviceManagerURIBuilder.build(path, params);
        log.info("URI: {}", uri);

        var responseType = getParameterizedTypeReference(serviceResponseType);
        var response = serviceManagerRestClient.get(uri, responseType);

        validatePaginatedResponse(response, name);

        var items = response.items();
        return items.getFirst();
    }

    private <T> ParameterizedTypeReference<PaginatedServiceResponseDto<T>> getParameterizedTypeReference(Class<T> itemType) {
        var resolvableType = ResolvableType.forClassWithGenerics(
                PaginatedServiceResponseDto.class,
                itemType
        );
        return ParameterizedTypeReference.forType(resolvableType.getType());
    }

    private <T> void validatePaginatedResponse(PaginatedServiceResponseDto<T> response, String serviceName) {
        if (isNull(response) || response.numItems() == 0) {
            throw new ServiceNotFoundException(serviceName);
        }
    }

    private void deleteById(String path, UUID id) {
        var pathSegments = String.join("/", path, id.toString());
        var uri = serviceManagerURIBuilder.build(pathSegments, null);
        serviceManagerRestClient.delete(uri);
    }
}
