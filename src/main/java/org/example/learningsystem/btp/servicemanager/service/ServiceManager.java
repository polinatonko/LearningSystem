package org.example.learningsystem.btp.servicemanager.service;

import org.example.learningsystem.btp.servicemanager.dto.ServiceBindingResponseDto;
import org.example.learningsystem.btp.servicemanager.dto.ServiceInstanceResponseDto;

import java.util.UUID;

public interface ServiceManager {

    ServiceInstanceResponseDto createServiceInstanceByOfferingAndPlanName(String name, String offeringName, String servicePlanName);

    ServiceBindingResponseDto createServiceBinding(String name, UUID serviceInstanceId);

    void deleteServiceInstanceByName(String name);

    void deleteServiceBindingByName(String name);
}
