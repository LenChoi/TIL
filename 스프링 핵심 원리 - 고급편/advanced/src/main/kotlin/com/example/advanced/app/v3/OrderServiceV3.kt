package com.example.advanced.app.v3

import com.example.advanced.trace.TraceId
import com.example.advanced.trace.TraceStatus
import com.example.advanced.trace.logtrace.LogTrace
import org.springframework.stereotype.Service

@Service
class OrderServiceV3(
    private val orderRepository: OrderRepositoryV3,
    private val trace: LogTrace
) {

    fun orderItem(traceId: TraceId, itemId: String) {
        var status: TraceStatus? = null

        try {
            status = trace.begin("OrderService.orderItem()")

            orderRepository.save(itemId)
            trace.end(status)
        } catch (e: Exception) {
            trace.exception(status!!, e)
            throw e
        }

    }
}