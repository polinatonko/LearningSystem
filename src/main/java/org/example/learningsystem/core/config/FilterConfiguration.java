package org.example.learningsystem.core.config;

import lombok.RequiredArgsConstructor;
import org.example.learningsystem.multitenancy.filter.RequestContextFilter;
import org.example.learningsystem.multitenancy.resolver.TenantResolver;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static org.example.learningsystem.core.config.constant.ApiConstants.API_ENDPOINTS;
import static org.example.learningsystem.core.config.constant.FilterChainOrderConstants.REQUEST_CONTEXT_FILTER_CHAIN_ORDER;

@Configuration
@RequiredArgsConstructor
public class FilterConfiguration {

    private final TenantResolver tenantResolver;

    @Bean
    public FilterRegistrationBean<RequestContextFilter> requestContextFilterFilterRegistrationBean() {
        var filterBean = new FilterRegistrationBean<RequestContextFilter>();
        filterBean.setFilter(new RequestContextFilter(tenantResolver));
        filterBean.setOrder(REQUEST_CONTEXT_FILTER_CHAIN_ORDER);
        filterBean.setUrlPatterns(List.of(API_ENDPOINTS));
        return filterBean;
    }
}
