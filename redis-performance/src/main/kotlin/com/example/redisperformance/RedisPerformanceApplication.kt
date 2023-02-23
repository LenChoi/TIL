package com.example.redisperformance

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RedisPerformanceApplication

fun main(args: Array<String>) {
    runApplication<RedisPerformanceApplication>(*args)
}
