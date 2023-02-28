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

class Solution {
    fun solution(cards1: Array<String>, cards2: Array<String>, goal: Array<String>): String {
        var answer = ""
        val cards1List = cards1.toMutableList()
        val cards2List = cards2.toMutableList()

        goal.forEach {
            answer = "No"
            if (cards1List.firstOrNull() == it) {
                cards1List.removeAt(0)
                answer = "Yes"
            }

            if (cards2List.firstOrNull() == it) {
                cards2List.removeAt(0)
                answer = "Yes"
            }

            if (answer == "No") return answer
        }

        return answer
    }
}

fun main() {
    val solution = Solution()

    val result = solution.solution(
        arrayOf("i", "water", "drink"), arrayOf("want", "to"), arrayOf("i", "want", "to", "drink", "water")
    )

    println(result)
}