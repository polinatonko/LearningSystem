package org.example.learningsystem.core.cache.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

import static org.example.learningsystem.core.cache.constant.CacheConstants.ACCESS_TOKENS_CACHE_MAXIMUM_SIZE;
import static org.example.learningsystem.core.cache.constant.CacheConstants.ACCESS_TOKENS_CACHE_NAME;
import static org.example.learningsystem.core.cache.constant.CacheConstants.ACCESS_TOKENS_CACHE_TTL_MINUTES;

@Configuration
@EnableCaching
public class CacheConfiguration {

    @Bean
    public CaffeineCacheManager caffeineCacheManager() {
        var cacheManager = new CaffeineCacheManager(ACCESS_TOKENS_CACHE_NAME);
        cacheManager.setCaffeine(cacheBuilder());
        return cacheManager;
    }

    public Caffeine<Object, Object> cacheBuilder() {
        return Caffeine.newBuilder()
                .maximumSize(ACCESS_TOKENS_CACHE_MAXIMUM_SIZE)
                .expireAfterWrite(Duration.ofMinutes(ACCESS_TOKENS_CACHE_TTL_MINUTES))
                .recordStats();
    }
}
