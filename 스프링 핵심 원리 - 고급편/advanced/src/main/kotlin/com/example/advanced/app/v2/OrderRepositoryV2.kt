package com.example.advanced.app.v2

import com.example.advanced.trace.hellotrace.HelloTraceV2
import com.example.advanced.trace.TraceId
import com.example.advanced.trace.TraceStatus
import org.springframework.stereotype.Repository

@Repository
class OrderRepositoryV2(
    private val trace: HelloTraceV2
) {

    fun save(traceId: TraceId, itemId: String) {
        var status: TraceStatus? = null

        try {
            status = trace.beginSync(traceId, "OrderRepository.save()")

            if (itemId == "ex") {
                throw IllegalArgumentException("예외 발생!")
            }

            trace.end(status)
        } catch (e: Exception) {
            throw e
        }

        sleep(1000)
    }

    private fun sleep(millis: Long) {
        try {
            Thread.sleep(millis)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }
}