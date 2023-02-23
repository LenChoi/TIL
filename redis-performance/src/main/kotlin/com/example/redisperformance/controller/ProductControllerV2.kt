package com.example.redisperformance.controller

import com.example.redisperformance.entity.Product
import com.example.redisperformance.service.ProductServiceV2
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/product/v2")
class ProductControllerV2(
    private val productServiceV2: ProductServiceV2
) {
    @GetMapping("/{id}")
    fun getProduct(@PathVariable id: Long): Mono<Product> {
        return productServiceV2.getProduct(id)
    }

    @PutMapping("/{id}")
    fun getProduct(@PathVariable id: Long, @RequestBody productMono: Mono<Product>): Mono<Product> {
        return productServiceV2.updateProduct(id, productMono)
    }

    @DeleteMapping("/{id}")
    fun deleteProduct(@PathVariable id: Long): Mono<Void> {
        return productServiceV2.deleteProduct(id)
    }
}