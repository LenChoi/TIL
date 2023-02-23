package com.example.redisspring

import com.example.reddisionplayground.config.RedissonConfig
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import org.redisson.api.RedissonReactiveClient

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class BaseTest {
    private val redissonConfig = RedissonConfig()
    lateinit var client: RedissonReactiveClient

    @BeforeAll
    fun seClient() {
        this.client = this.redissonConfig.getReactiveClient()
    }

    @AfterAll
    fun shutDown() {
        this.client.shutdown()
    }

    fun sleep(millis: Long) {
        Thread.sleep(millis)
    }
}