package com.example.redisperformance.util

import com.example.redisperformance.entity.Product
import com.example.redisperformance.repository.ProductRepository
import org.redisson.api.RMapReactive
import org.redisson.api.RedissonReactiveClient
import org.redisson.codec.TypedJsonJacksonCodec
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class ProductCacheTemplate(
    private val productRepository: ProductRepository,
    client: RedissonReactiveClient
) : CacheTemplate<Long, Product>() {

    lateinit var map: RMapReactive<Long, Product>

    init {
        map = client.getMap("product", TypedJsonJacksonCodec(Long::class.java, Product::class.java))
    }

    override fun getFromSource(id: Long): Mono<Product> {
        return productRepository.findById(id)
    }

    override fun getFromCache(id: Long): Mono<Product> {
        return map[id]
    }

    override fun updateSource(id: Long, product: Product): Mono<Product> {
        return productRepository.findById(id).doOnNext { p ->
            product.id = id
        }.flatMap { p -> productRepository.save(product) }
    }

    override fun updateCache(id: Long, product: Product): Mono<Product> {
        return map.fastPut(id, product).thenReturn(product)
    }

    override fun deleteFromSource(id: Long): Mono<Void> {
        return productRepository.deleteById(id)
    }

    override fun deleteFromCache(id: Long): Mono<Void> {
        return map.fastRemove(id).then()
    }
}