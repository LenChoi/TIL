package com.example.redisspring

import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.Test
import org.redisson.api.RedissonReactiveClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.redis.core.ReactiveHashOperations
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.data.redis.core.ReactiveStringRedisTemplate
import reactor.core.publisher.Flux
import reactor.test.StepVerifier

@SpringBootTest
class RedisSpringApplicationTests : BaseTest() {
    @RepeatedTest(3)
    fun redissonTest() {
        val atomicLong = this.client.getAtomicLong("user:2:visit")

        val before = System.currentTimeMillis()

        val mono = Flux.range(1, 500_000).flatMap { i ->
            atomicLong.incrementAndGet()
        }.then()

        StepVerifier.create(mono).verifyComplete()

        val after = System.currentTimeMillis()
        println("${after - before}")
    }
}
