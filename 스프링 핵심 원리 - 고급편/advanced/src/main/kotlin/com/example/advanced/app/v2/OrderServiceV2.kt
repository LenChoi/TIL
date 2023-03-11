package com.example.advanced.app.v2

import com.example.advanced.app.v3.OrderRepositoryV3
import com.example.advanced.trace.hellotrace.HelloTraceV2
import com.example.advanced.trace.TraceId
import com.example.advanced.trace.TraceStatus
import org.springframework.stereotype.Service

@Service
class OrderServiceV2(
    private val orderRepository: OrderRepositoryV3,
    private val trace: HelloTraceV2
) {

    fun orderItem(traceId: TraceId, itemId: String) {
        var status: TraceStatus? = null

        try {
            status = trace.beginSync(traceId, "OrderService.orderItem()")

            orderRepository.save(itemId)
            trace.end(status)
        } catch (e: Exception) {
            trace.exception(status!!, e)
            throw e
        }

    }
}