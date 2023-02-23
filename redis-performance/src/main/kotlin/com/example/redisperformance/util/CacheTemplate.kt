package com.example.redisperformance.util

import reactor.core.publisher.Mono

abstract class CacheTemplate<KEY, ENTITY> {

    fun get(key: KEY): Mono<ENTITY> {
        return getFromCache(key).switchIfEmpty(getFromSource(key).flatMap { e -> updateCache(key, e) })
    }

    fun update(key: KEY, entity: ENTITY): Mono<ENTITY> {
        return updateSource(key,entity).flatMap { e -> deleteFromCache(key).thenReturn(e) }
    }

    fun delete(key: KEY): Mono<Void> {
        return deleteFromSource(key).then(deleteFromCache(key))
    }

    abstract fun getFromSource(key: KEY): Mono<ENTITY>
    abstract fun getFromCache(key: KEY): Mono<ENTITY>
    abstract fun updateSource(key: KEY, entity: ENTITY): Mono<ENTITY>
    abstract fun updateCache(key: KEY, entity: ENTITY): Mono<ENTITY>
    abstract fun deleteFromSource(key: KEY): Mono<Void>
    abstract fun deleteFromCache(key: KEY): Mono<Void>
}