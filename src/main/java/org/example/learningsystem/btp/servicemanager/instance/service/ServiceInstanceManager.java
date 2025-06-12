package org.example.learningsystem.btp.servicemanager.instance.service;

import org.example.learningsystem.btp.servicemanager.common.util.ServiceManagerRestClient;
import org.example.learningsystem.btp.servicemanager.common.util.ServiceManagerURIBuilder;
import org.example.learningsystem.btp.servicemanager.instance.dto.CreateServiceInstanceByOfferingAndPlanName;
import org.example.learningsystem.btp.servicemanager.instance.dto.ServiceInstanceResponseDto;
import org.example.learningsystem.btp.servicemanager.common.service.BaseServiceManager;
import org.example.learningsystem.btp.servicemanager.common.util.ServiceManagerRestClientImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Map;

import static org.example.learningsystem.btp.servicemanager.common.constant.ServiceManagerResourceConstants.DATABASE_ID;
import static org.example.learningsystem.btp.servicemanager.common.constant.ServiceManagerResourceConstants.NAME;
import static org.example.learningsystem.btp.servicemanager.common.constant.ServiceManagerResourceConstants.SERVICE_INSTANCES;

@Service
@Profile("cloud")
public class ServiceInstanceManager {

    private final BaseServiceManager baseServiceManager;
    private final String databaseId;
    private final ServiceManagerRestClient serviceManagerRestClient;
    private final ServiceManagerURIBuilder serviceManagerURIBuilder;

    public ServiceInstanceManager(@Value("${vcap.services.lms-hana-schema.credentials.database_id}") String databaseId,
                                  BaseServiceManager baseServiceManager,
                                  ServiceManagerRestClientImpl serviceManagerRestClientImpl,
                                  ServiceManagerURIBuilder serviceManagerURIBuilder) {
        this.baseServiceManager = baseServiceManager;
        this.serviceManagerRestClient = serviceManagerRestClientImpl;
        this.serviceManagerURIBuilder = serviceManagerURIBuilder;
        this.databaseId = databaseId;
    }

    public ServiceInstanceResponseDto createByOfferingAndPlanName(String name, String offering, String servicePlan) {
        var uri = serviceManagerURIBuilder.builder(SERVICE_INSTANCES)
                .async(false)
                .build();
        var parameters = Map.of(DATABASE_ID, databaseId);
        var body = new CreateServiceInstanceByOfferingAndPlanName(name, offering, servicePlan, parameters);
        return serviceManagerRestClient.post(uri, body, ServiceInstanceResponseDto.class);
    }

    public void deleteByName(String name) {
        var instance = baseServiceManager.getByField(NAME, name, SERVICE_INSTANCES, ServiceInstanceResponseDto.class);
        baseServiceManager.deleteById(SERVICE_INSTANCES, instance.id());
    }
}
