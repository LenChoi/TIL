package com.example.advanced.v0

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class OrderControllerV0(
    private val orderServiceV0: OrderServiceV0
) {

    @GetMapping("/v0/request")
    fun request(itemId: String): String {
        orderServiceV0.orderItem(itemId)

        return "ok"
    }
}