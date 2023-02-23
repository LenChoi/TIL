package com.example.redisperformance.service

import com.example.redisperformance.entity.Product
import com.example.redisperformance.util.CacheTemplate
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class ProductServiceV2(
    private val cacheTemplate: CacheTemplate<Long, Product>
) {
    fun getProduct(id: Long): Mono<Product> {
        return cacheTemplate.get(id)
    }

    fun updateProduct(id: Long, productMono: Mono<Product>): Mono<Product> {
        return productMono.flatMap { p -> cacheTemplate.update(id, p) }
    }

    fun deleteProduct(id: Long): Mono<Void> {
        return cacheTemplate.delete(id)
    }
}