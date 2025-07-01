package org.example.learningsystem.core.cache.constant;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class CacheConstants {

    public static final String ACCESS_TOKENS_CACHE_NAME = "access_tokens";
    public static final int ACCESS_TOKENS_CACHE_MAXIMUM_SIZE = 500;
    public static final int ACCESS_TOKENS_CACHE_TTL_MINUTES = 3;
}
