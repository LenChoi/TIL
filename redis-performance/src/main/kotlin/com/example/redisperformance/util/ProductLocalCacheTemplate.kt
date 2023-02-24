package com.example.redisperformance.util

import com.example.redisperformance.entity.Product
import com.example.redisperformance.repository.ProductRepository
import org.redisson.api.LocalCachedMapOptions
import org.redisson.api.RLocalCachedMap
import org.redisson.api.RedissonClient
import org.redisson.codec.TypedJsonJacksonCodec
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class ProductLocalCacheTemplate(
    private val productRepository: ProductRepository,
    client: RedissonClient
) : CacheTemplate<Long, Product>() {
    val mapOptions =
        LocalCachedMapOptions.defaults<Long, Product>().syncStrategy(LocalCachedMapOptions.SyncStrategy.UPDATE)
            .reconnectionStrategy(LocalCachedMapOptions.ReconnectionStrategy.CLEAR)
    lateinit var map: RLocalCachedMap<Long, Product>

    init {
        map = client.getLocalCachedMap(
            "product", TypedJsonJacksonCodec(Long::class.java, Product::class.java), mapOptions
        )
    }

    override fun getFromSource(id: Long): Mono<Product> {
        return productRepository.findById(id)
    }

    override fun getFromCache(id: Long): Mono<Product> {
        return Mono.justOrEmpty(map[id])
    }

    override fun updateSource(id: Long, product: Product): Mono<Product> {
        return productRepository.findById(id).doOnNext { p ->
            product.id = id
        }.flatMap { p -> productRepository.save(product) }
    }

    override fun updateCache(id: Long, product: Product): Mono<Product> {
        return Mono.create { sink ->
            map.fastPutAsync(id, product)
                .thenAccept { b -> sink.success(product) }
                .exceptionally{ex ->
                    sink.error(ex)
                    return@exceptionally null
                }
        }
    }

    override fun deleteFromSource(id: Long): Mono<Void> {
        return productRepository.deleteById(id)
    }

    override fun deleteFromCache(id: Long): Mono<Void> {
        return Mono.create { sink ->
            map.fastRemoveAsync( id)
                .thenAccept { b -> sink.success() }
                .exceptionally{ex ->
                    sink.error(ex)
                    return@exceptionally null
                }
        }
    }
}