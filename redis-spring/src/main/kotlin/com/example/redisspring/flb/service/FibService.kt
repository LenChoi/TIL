package com.example.redisspring.flb.service

import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class FibService {
    // have a strategy for cache evict
    @Cacheable(value = ["math:fib"], key = "#index") //bucket prefix, 인덱스를 키로 설정 아니면 두 파라미터 다 키가 되기 때문에
    fun getFib(index: Int): Int { // 파라미터는 해시 키로 사용
        println("calculating fib for  $index")
        return this.fib(index)
    }

    // PUT / POST / PATCH / DELETE
    @CacheEvict(value = ["math:fib"], key = "#index")
    fun clearCache(index: Int) {
        println("clearing hash key")
    }

//    @Scheduled(fixedRate = 10_000)
    @CacheEvict(value = ["math:fib"], allEntries = true)
    fun clearCache() {
        println("clearing all fib keys")
    }

    //intentional 2^n
    private fun fib(index: Int): Int {
        if (index < 2) return index
        return fib(index - 1) + fib(index - 2)
    }
}