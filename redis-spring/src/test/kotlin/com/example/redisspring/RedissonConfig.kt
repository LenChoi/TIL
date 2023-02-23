package com.example.redisspring

import org.redisson.Redisson
import org.redisson.api.RedissonClient
import org.redisson.api.RedissonReactiveClient
import org.redisson.config.Config

class RedissonConfig{
    private lateinit var redissonClient: RedissonClient

    private fun getClient(): RedissonClient {
        val config = Config()

        config.useSingleServer().address = "redis://127.0.0.1:6379"

        redissonClient = Redisson.create(config)

        return redissonClient
    }

    fun getReactiveClient(): RedissonReactiveClient {
        return getClient().reactive()
    }
}