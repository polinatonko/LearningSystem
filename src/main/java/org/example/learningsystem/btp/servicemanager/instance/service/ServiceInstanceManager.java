package org.example.learningsystem.btp.servicemanager.instance.service;

import org.example.learningsystem.btp.servicemanager.common.util.ServiceManagerURIBuilder;
import org.example.learningsystem.btp.servicemanager.common.validator.ServiceManagerResponseValidator;
import org.example.learningsystem.btp.servicemanager.instance.dto.CreateServiceInstanceByOfferingAndPlanName;
import org.example.learningsystem.btp.servicemanager.instance.dto.ServiceInstanceResponseDto;
import org.example.learningsystem.btp.servicemanager.common.service.BaseServiceManager;
import org.example.learningsystem.btp.servicemanager.common.util.ServiceManagerRestClientImpl;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import static org.example.learningsystem.btp.servicemanager.common.constant.ServiceManagerResourceConstants.NAME;
import static org.example.learningsystem.btp.servicemanager.common.constant.ServiceManagerResourceConstants.SERVICE_INSTANCES;

@Component
@Profile("cloud")
public class ServiceInstanceManager extends BaseServiceManager {

    public ServiceInstanceManager(ServiceManagerResponseValidator serviceManagerResponseValidator,
                                  ServiceManagerRestClientImpl serviceManagerRestClientImpl,
                                  ServiceManagerURIBuilder serviceManagerURIBuilder) {
        super(serviceManagerResponseValidator, serviceManagerRestClientImpl, serviceManagerURIBuilder);
    }

    public ServiceInstanceResponseDto createByOfferingAndPlanName(String name, String offering, String servicePlan) {
        var uri = serviceManagerURIBuilder.builder(SERVICE_INSTANCES)
                .async(false)
                .build();
        var body = new CreateServiceInstanceByOfferingAndPlanName(name, offering, servicePlan);
        return serviceManagerRestClient.post(uri, body, ServiceInstanceResponseDto.class);
    }

    public void deleteByName(String name) {
        var instance = getServiceByField(NAME, name, SERVICE_INSTANCES, ServiceInstanceResponseDto.class);
        deleteById(SERVICE_INSTANCES, instance.id());
    }
}
