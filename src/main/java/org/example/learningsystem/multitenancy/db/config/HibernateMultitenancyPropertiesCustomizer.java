package org.example.learningsystem.multitenancy.db.config;

import lombok.RequiredArgsConstructor;
import org.example.learningsystem.multitenancy.db.resolver.TenantIdentifierResolver;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.stereotype.Component;

import java.util.Map;

import static org.hibernate.cfg.MultiTenancySettings.MULTI_TENANT_CONNECTION_PROVIDER;
import static org.hibernate.cfg.MultiTenancySettings.MULTI_TENANT_IDENTIFIER_RESOLVER;

/**
 * Customizes Hibernate properties for multitenancy support.
 */
@Component
@RequiredArgsConstructor
public class HibernateMultitenancyPropertiesCustomizer implements HibernatePropertiesCustomizer {

    private final MultiTenantConnectionProvider<String> multiTenantConnectionProvider;
    private final TenantIdentifierResolver tenantIdentifierResolver;

    /**
     * Customizes Hibernate properties by adding multitenancy configurations.
     *
     * @param hibernateProperties a map of existing Hibernate properties
     */
    @Override
    public void customize(Map<String, Object> hibernateProperties) {
        hibernateProperties.put(MULTI_TENANT_CONNECTION_PROVIDER, multiTenantConnectionProvider);
        hibernateProperties.put(MULTI_TENANT_IDENTIFIER_RESOLVER, tenantIdentifierResolver);
    }
}
