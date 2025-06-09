package org.example.learningsystem.btp.servicemanager.util;

import lombok.RequiredArgsConstructor;
import org.example.learningsystem.btp.servicemanager.config.ServiceManagerProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
public class ServiceManagerURIBuilder {

    private static final String SERVICE_MANAGER_PATH = "v1";

    private final ServiceManagerProperties serviceManagerProperties;

    public URI build(String pathSegments, Map<String, String> queryParams) {
        if (isNull(queryParams)) {
            queryParams = new HashMap<>();
        }
        return UriComponentsBuilder.fromUriString(serviceManagerProperties.getUrl())
                .pathSegment(SERVICE_MANAGER_PATH)
                .pathSegment(pathSegments.split("/"))
                .queryParams(MultiValueMap.fromSingleValue(queryParams))
                .build()
                .toUri();
    }
}
