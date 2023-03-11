package com.example.advanced.app.v3

import com.example.advanced.trace.TraceStatus
import com.example.advanced.trace.logtrace.LogTrace
import org.springframework.stereotype.Repository

@Repository
class OrderRepositoryV3(
    private val trace: LogTrace
) {

    fun save(itemId: String) {
        var status: TraceStatus? = null

        try {
            status = trace.begin("OrderRepository.save()")

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