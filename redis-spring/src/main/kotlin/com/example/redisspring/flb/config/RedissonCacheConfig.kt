package com.example.redisspring.flb.config

import org.redisson.api.RedissonClient
import org.redisson.spring.cache.RedissonSpringCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RedissonCacheConfig {

    @Bean
    fun cacheManager(redissonClient: RedissonClient): RedissonSpringCacheManager {
        return RedissonSpringCacheManager(redissonClient)
    }
}