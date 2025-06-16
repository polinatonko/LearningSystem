package org.example.learningsystem.btp.servicemanager.common.builder;

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

/**
 * Provides methods for constructing URIs to access the Service Manager API.
 */
@Component
@Profile("cloud")
@RequiredArgsConstructor(access = PROTECTED)
public class ServiceManagerURIBuilder {

    private final ServiceManagerProperties serviceManagerProperties;

    private UriComponentsBuilder uriComponentsBuilder;

    private ServiceManagerURIBuilder(ServiceManagerProperties serviceManagerProperties,
                                     UriComponentsBuilder uriComponentsBuilder) {
        this.serviceManagerProperties = serviceManagerProperties;
        this.uriComponentsBuilder = uriComponentsBuilder;
    }

    /**
     * Creates a new URI builder initialized with the base uri of the Service Manager API
     * and the specified path segments.
     *
     * @param pathSegments the path segments to include in the URI
     * @return a new {@link ServiceManagerURIBuilder} instance
     */
    public ServiceManagerURIBuilder builder(String pathSegments) {
        var builder = UriComponentsBuilder.fromUriString(serviceManagerProperties.getUrl())
                .pathSegment(BASE_PATH)
                .pathSegment(pathSegments.split("/"));
        return new ServiceManagerURIBuilder(serviceManagerProperties, builder);
    }

    /**
     * Adds a field query to the URI builder.
     *
     * @param field the name of the field
     * @param value the value of the field
     * @return a new {@link ServiceManagerURIBuilder} instance
     */
    public ServiceManagerURIBuilder fieldQuery(String field, String value) {
        var query = EQUALS_QUERY.formatted(field, value);
        var builder = uriComponentsBuilder.queryParam(FIELD_QUERY, query);
        return new ServiceManagerURIBuilder(serviceManagerProperties, builder);
    }

    /**
     * Adds a label query to the URI builder.
     *
     * @param label the name of the field
     * @param value the value of the field
     * @return a new {@link ServiceManagerURIBuilder} instance
     */
    public ServiceManagerURIBuilder labelQuery(String label, String value) {
        var query = EQUALS_QUERY.formatted(label, value);
        var builder = uriComponentsBuilder.queryParam(LABEL_QUERY, query);
        return new ServiceManagerURIBuilder(serviceManagerProperties, builder);
    }

    /**
     * Adds 'async' request parameter to the URI builder.
     *
     * @param isAsync the value of the parameter
     * @return a new {@link ServiceManagerURIBuilder} instance
     */
    public ServiceManagerURIBuilder async(boolean isAsync) {
        var builder = uriComponentsBuilder.queryParam("async", isAsync);
        return new ServiceManagerURIBuilder(serviceManagerProperties, builder);
    }

    /**
     * Builds a URI from the {@link UriComponentsBuilder}.
     *
     * @return a new {@link URI} instance
     */
    public URI build() {
        return uriComponentsBuilder.build().toUri();
    }
}
