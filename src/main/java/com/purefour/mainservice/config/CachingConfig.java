package com.purefour.mainservice.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Arrays;
import java.util.Objects;

@Slf4j
@Configuration
@EnableCaching
public class CachingConfig {

    @Bean
    public CacheManager cacheManager() {
        final SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(Arrays.asList(
                new ConcurrentMapCache("scanProducts"),
                new ConcurrentMapCache("allegroApiTokenResponse")
        ));
        return cacheManager;
    }

    @Scheduled(fixedRate = 43200000) // every 12h
    public void evictCachedApiKeys() {
        log.info("Clearing API keys cache...");
        final Cache allegroApiTokenResponseCache = cacheManager().getCache("allegroApiTokenResponse");
        if (Objects.nonNull(allegroApiTokenResponseCache))
            allegroApiTokenResponseCache.clear();
    }
}
