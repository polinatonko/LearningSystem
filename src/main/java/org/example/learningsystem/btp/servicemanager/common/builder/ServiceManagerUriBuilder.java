package org.example.learningsystem.btp.servicemanager.common.builder;

import lombok.RequiredArgsConstructor;
import org.example.learningsystem.btp.servicemanager.common.config.ServiceManagerProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static org.example.learningsystem.btp.servicemanager.common.constant.ServiceManagerResourceConstants.BASE_PATH;
import static org.example.learningsystem.btp.servicemanager.common.constant.ServiceManagerResourceConstants.EQUALS_QUERY;
import static org.example.learningsystem.btp.servicemanager.common.constant.ServiceManagerResourceConstants.FIELD_QUERY;
import static org.example.learningsystem.btp.servicemanager.common.constant.ServiceManagerResourceConstants.LABEL_QUERY;

/**
 * Provides methods for constructing URIs to access the Service Manager API.
 */
@Component
@Profile("cloud")
@RequiredArgsConstructor
public class ServiceManagerUriBuilder {

    private final ServiceManagerProperties serviceManagerProperties;

    private UriComponentsBuilder uriComponentsBuilder;

    /**
     * Creates a new URI builder initialized with the base uri of the Service Manager API
     * and the specified path segments.
     *
     * @param pathSegments the path segments to include in the URI
     * @return a {@link ServiceManagerUriBuilder} instance
     */
    public ServiceManagerUriBuilder builder(String pathSegments) {
        uriComponentsBuilder = UriComponentsBuilder.fromUriString(serviceManagerProperties.getUrl())
                .pathSegment(BASE_PATH)
                .pathSegment(pathSegments.split("/"));
        return this;
    }

    /**
     * Adds a field query to the URI builder.
     *
     * @param field the name of the field
     * @param value the value of the field
     * @return a {@link ServiceManagerUriBuilder} instance
     */
    public ServiceManagerUriBuilder fieldQuery(String field, String value) {
        var query = EQUALS_QUERY.formatted(field, value);
        uriComponentsBuilder = uriComponentsBuilder.queryParam(FIELD_QUERY, query);
        return this;
    }

    /**
     * Adds a label query to the URI builder.
     *
     * @param label the name of the field
     * @param value the value of the field
     * @return a {@link ServiceManagerUriBuilder} instance
     */
    public ServiceManagerUriBuilder labelQuery(String label, String value) {
        var query = EQUALS_QUERY.formatted(label, value);
        uriComponentsBuilder = uriComponentsBuilder.queryParam(LABEL_QUERY, query);
        return this;
    }

    /**
     * Adds 'async' request parameter to the URI builder.
     *
     * @param isAsync the value of the parameter
     * @return a {@link ServiceManagerUriBuilder} instance
     */
    public ServiceManagerUriBuilder async(boolean isAsync) {
        uriComponentsBuilder = uriComponentsBuilder.queryParam("async", isAsync);
        return this;
    }

    /**
     * Builds a URI from the {@link UriComponentsBuilder}.
     *
     * @return constructed {@link URI} instance
     */
    public URI build() {
        return uriComponentsBuilder.build()
                .toUri();
    }
}
