package com.example.redisperformance.controller

import com.example.redisperformance.entity.Product
import com.example.redisperformance.service.ProductServiceV1
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/product/v1")
class ProductControllerV1(
    private val productServiceV1: ProductServiceV1
) {
    @GetMapping("/{id}")
    fun getProduct(@PathVariable id: Long): Mono<Product> {
        return productServiceV1.getProduct(id)
    }

    @PutMapping("/{id}")
    fun getProduct(@PathVariable id: Long, @RequestBody productMono: Mono<Product>): Mono<Product> {
        return productServiceV1.updateProduct(id, productMono)
    }
}