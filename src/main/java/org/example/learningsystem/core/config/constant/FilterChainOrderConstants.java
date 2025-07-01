package org.example.learningsystem.core.config.constant;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

/**
 * Provides values for the execution order of all filters in the application (a lower number indicates higher precedence).
 */
@NoArgsConstructor(access = PRIVATE)
public class FilterChainOrderConstants {

    public static final int ACTUATOR_FILTER_CHAIN_ORDER = 0;
    public static final int API_FILTER_CHAIN_ORDER = 1;
    public static final int REQUEST_CONTEXT_FILTER_CHAIN_ORDER = 2;
}
