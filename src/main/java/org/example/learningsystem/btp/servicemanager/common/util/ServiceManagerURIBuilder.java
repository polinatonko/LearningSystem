package org.example.learningsystem.btp.servicemanager.common.util;

import lombok.RequiredArgsConstructor;
import org.example.learningsystem.btp.servicemanager.common.config.ServiceManagerProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static lombok.AccessLevel.PROTECTED;
import static org.example.learningsystem.btp.servicemanager.common.constant.ServiceManagerResourceConstants.BASE_PATH;
import static org.example.learningsystem.btp.servicemanager.common.constant.ServiceManagerResourceConstants.EQUALS_QUERY;
import static org.example.learningsystem.btp.servicemanager.common.constant.ServiceManagerResourceConstants.FIELD_QUERY;
import static org.example.learningsystem.btp.servicemanager.common.constant.ServiceManagerResourceConstants.LABEL_QUERY;

@Component
@Profile("cloud")
@RequiredArgsConstructor(access = PROTECTED)
public class ServiceManagerURIBuilder {

    private final ServiceManagerProperties serviceManagerProperties;

    private UriComponentsBuilder uriComponentsBuilder;

    public ServiceManagerURIBuilder builder(String pathSegments) {
        uriComponentsBuilder = UriComponentsBuilder.fromUriString(serviceManagerProperties.getUrl())
                .pathSegment(BASE_PATH)
                .pathSegment(pathSegments.split("/"));
        return this;
    }

    public ServiceManagerURIBuilder fieldQuery(String field, String value) {
        var query = EQUALS_QUERY.formatted(field, value);
        uriComponentsBuilder = uriComponentsBuilder.queryParam(FIELD_QUERY, query);
        return this;
    }

    public ServiceManagerURIBuilder labelQuery(String label, String value) {
        var query = EQUALS_QUERY.formatted(label, value);
        uriComponentsBuilder = uriComponentsBuilder.queryParam(LABEL_QUERY, query);
        return this;
    }

    public ServiceManagerURIBuilder async(boolean isAsync) {
        uriComponentsBuilder = uriComponentsBuilder.queryParam("async", isAsync);
        return this;
    }

    public URI build() {
        return uriComponentsBuilder.build().toUri();
    }
}
