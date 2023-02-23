package com.example.redisspring.flb.controller

import com.example.redisspring.flb.service.FibService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("fib")
class FibController(
    private val fibService: FibService
) {
    @GetMapping("{index}")
    fun getFib(@PathVariable index: Int): Mono<Int> {
        return Mono.fromSupplier { this.fibService.getFib(index) }
    }

    @GetMapping("/{index}/clear")
    fun clearCache(@PathVariable index: Int): Mono<Void> {
        return Mono.fromRunnable{ this.fibService.clearCache(index) }
    }
}