package com.example.redisperformance.service

import com.example.redisperformance.entity.Product
import com.example.redisperformance.repository.ProductRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.core.io.Resource
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.stereotype.Service
import org.springframework.util.StreamUtils
import reactor.core.publisher.Flux
import java.nio.charset.StandardCharsets
import java.util.concurrent.ThreadLocalRandom

@Service
class DataSetupService(
    private val productRepository: ProductRepository,
    private val r2dbcEntityTemplate: R2dbcEntityTemplate,

    @Value("classpath:schema.sql")
    private val resource: Resource
) : CommandLineRunner {

    override fun run(vararg args: String) {
        val query = StreamUtils.copyToString(resource.inputStream, StandardCharsets.UTF_8)

        println(query)

        val insert = Flux.range(1, 1000)
            .map { i -> Product(null, "product $i", ThreadLocalRandom.current().nextInt(1, 100).toBigDecimal()) }
            .collectList().flatMapMany { productRepository.saveAll(it) }.then()

        r2dbcEntityTemplate.databaseClient.sql(query).then().then(insert).doFinally { s -> println("data setup done $s") }.subscribe()
    }
}