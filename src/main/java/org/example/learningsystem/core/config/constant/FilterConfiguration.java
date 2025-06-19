package org.example.learningsystem.core.config.constant;

import lombok.RequiredArgsConstructor;
import org.example.learningsystem.multitenancy.filter.TenantIdentifierFilter;
import org.example.learningsystem.multitenancy.resolver.TenantResolver;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.example.learningsystem.core.config.constant.FilterChainOrderConstants.REQUEST_CONTEXT_FILTER_CHAIN_ORDER;

@Configuration
@RequiredArgsConstructor
public class FilterConfiguration {

    private final TenantResolver tenantResolver;

    @Bean
    public FilterRegistrationBean<TenantIdentifierFilter> requestContextFilterFilterRegistrationBean() {
        var filterBean = new FilterRegistrationBean<TenantIdentifierFilter>();
        filterBean.setFilter(new TenantIdentifierFilter(tenantResolver));
        filterBean.setOrder(REQUEST_CONTEXT_FILTER_CHAIN_ORDER);
        return filterBean;
    }
}
