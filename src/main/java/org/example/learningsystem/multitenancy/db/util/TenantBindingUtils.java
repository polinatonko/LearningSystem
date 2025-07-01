package org.example.learningsystem.multitenancy.db.util;

import lombok.NoArgsConstructor;
import org.example.learningsystem.btp.servicemanager.binding.dto.ServiceBindingResponseDto;
import org.example.learningsystem.multitenancy.context.TenantInfo;

import static lombok.AccessLevel.PRIVATE;
import static org.example.learningsystem.btp.servicemanager.common.constant.ServiceManagerResourceConstants.SUBDOMAIN;
import static org.example.learningsystem.btp.servicemanager.common.constant.ServiceManagerResourceConstants.TENANT_ID;

/**
 * Utility class for working with tenant information in cloud.
 * <p>
 * Provides methods for extracting tenant information from SAP BTP service bindings.
 */
@NoArgsConstructor(access = PRIVATE)
public class TenantBindingUtils {

    /**
     * Extracts tenant information from the service binding response.
     *
     * @param binding a {@link ServiceBindingResponseDto} instance
     * @return a {@link TenantInfo} instance
     */
    public static TenantInfo extractTenantInfo(ServiceBindingResponseDto binding) {
        var labels = binding.labels();
        var tenantId = labels.get(TENANT_ID).getFirst();
        var subdomain = labels.get(SUBDOMAIN).getFirst();
        return new TenantInfo(tenantId, subdomain);
    }
}
