package org.example.learningsystem.core.cache.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private static final String ACCESS_TOKENS_CACHE_NAME = "access_tokens";
    private static final int ACCESS_TOKENS_CACHE_TTL_MINUTES = 60 * 12;
    private static final int DEFAULT_CACHE_MAXIMUM_SIZE = 500;
    private static final int DEFAULT_CACHE_TTL_MINUTES = 3;

    @Bean
    public CaffeineCacheManager caffeineCacheManager() {
        var cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(defaultCacheBuilder());
        cacheManager.registerCustomCache(ACCESS_TOKENS_CACHE_NAME, accessTokenCacheBuilder().build());
        return cacheManager;
    }

    public Caffeine<Object, Object> defaultCacheBuilder() {
        return Caffeine.newBuilder()
                .maximumSize(DEFAULT_CACHE_MAXIMUM_SIZE)
                .expireAfterWrite(Duration.ofMinutes(DEFAULT_CACHE_TTL_MINUTES));
    }

    public Caffeine<Object, Object> accessTokenCacheBuilder() {
        return Caffeine.newBuilder()
                .expireAfterWrite(Duration.ofMinutes(ACCESS_TOKENS_CACHE_TTL_MINUTES))
                .recordStats();
    }
}
