package com.example.advanced.app.v2

import com.example.advanced.app.v3.OrderServiceV3
import com.example.advanced.trace.hellotrace.HelloTraceV2
import com.example.advanced.trace.TraceStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class OrderControllerV2(
    private val orderService: OrderServiceV3,
    private val trace: HelloTraceV2
) {

    @GetMapping("/v2/request")
    fun request(itemId: String): String {
        var status: TraceStatus? = null

        try {
            status = trace.begin("OrderController.request()")
            orderService.orderItem(status.traceId, itemId)
            trace.end(status)

            return "ok"
        } catch (e: Exception) {
            trace.exception(status!!, e)
            throw e
        }
    }
}