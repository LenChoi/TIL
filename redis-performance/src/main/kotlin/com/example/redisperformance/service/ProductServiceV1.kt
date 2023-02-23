package com.example.redisperformance.service

import com.example.redisperformance.entity.Product
import com.example.redisperformance.repository.ProductRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class ProductServiceV1(
    private val productRepository: ProductRepository
) {
    fun getProduct(id: Long): Mono<Product> {
        return productRepository.findById(id)
    }

    fun updateProduct(id: Long, productMono: Mono<Product>): Mono<Product> {
        return productRepository.findById(id).flatMap { productMono.doOnNext { it.id = id } }
            .flatMap(productRepository::save)
    }
}