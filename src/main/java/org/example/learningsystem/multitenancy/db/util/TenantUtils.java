package org.example.learningsystem.multitenancy.db.util;

import lombok.NoArgsConstructor;
import org.example.learningsystem.btp.servicemanager.binding.dto.ServiceBindingResponseDto;
import org.example.learningsystem.multitenancy.context.TenantInfo;

import static lombok.AccessLevel.PRIVATE;
import static org.example.learningsystem.btp.servicemanager.common.constant.ServiceManagerResourceConstants.SUBDOMAIN;
import static org.example.learningsystem.btp.servicemanager.common.constant.ServiceManagerResourceConstants.TENANT_ID;

@NoArgsConstructor(access = PRIVATE)
public class TenantUtils {

    public static TenantInfo extractTenantInfo(ServiceBindingResponseDto binding) {
        var labels = binding.labels();
        var tenantId = labels.get(TENANT_ID).getFirst();
        var subdomain = labels.get(SUBDOMAIN).getFirst();
        return new TenantInfo(tenantId, subdomain);
    }
}
