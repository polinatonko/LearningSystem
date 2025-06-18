package org.example.learningsystem.multitenancy.context;

import org.springframework.core.task.TaskDecorator;

public class TenantInfoTaskDecorator implements TaskDecorator {

    @Override
    public Runnable decorate(Runnable runnable) {
        var tenant = TenantContext.getTenant();
        return () -> {
            TenantContext.setTenant(tenant);
            try {
                runnable.run();
            } finally {
                TenantContext.clear();
            }
        };
    }
}