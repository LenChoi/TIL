package com.example.advanced.app.v3

import com.example.advanced.app.v3.OrderServiceV3
import com.example.advanced.trace.TraceStatus
import com.example.advanced.trace.logtrace.LogTrace
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class OrderControllerV3(
    private val orderService: OrderServiceV3,
    private val trace: LogTrace
) {

    @GetMapping("/v3/request")
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