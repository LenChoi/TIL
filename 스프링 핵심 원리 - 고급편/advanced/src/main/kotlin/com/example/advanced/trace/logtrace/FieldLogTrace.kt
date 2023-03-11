package com.example.advanced.trace.logtrace

import com.example.advanced.trace.TraceId
import com.example.advanced.trace.TraceStatus
import mu.KotlinLogging

private val logger = KotlinLogging.logger { }

class FieldLogTrace : LogTrace {
    private var traceIdHolder: TraceId? = null // 동시성 이슈 발생

    override fun begin(message: String): TraceStatus {
        syncTraceId()
        val traceId = traceIdHolder!!
        val startTimeMs = System.currentTimeMillis()
        logger.info("[{}] {}{}", traceId.id, addSpace(START_PREFIX, traceId.level), message)

        return TraceStatus(traceId, startTimeMs, message)
    }

    private fun syncTraceId() {
        if (traceIdHolder == null) {
            traceIdHolder = TraceId()
        } else {
            traceIdHolder = traceIdHolder!!.createNextId()
        }
    }

    override fun end(status: TraceStatus) {
        complete(status, null)
    }

    override fun exception(status: TraceStatus, e: Exception) {
        complete(status, e)
    }

    private fun complete(status: TraceStatus, e: Exception?) {
        val stopTimeMs = System.currentTimeMillis()
        val resultTimeMs: Long = stopTimeMs - status.startTimeMs
        val traceId: TraceId = status.traceId

        if (e == null) {
            logger.info(
                "[{}] {}{} time={}ms",
                traceId.id,
                addSpace(COMPLETE_PREFIX, traceId.level),
                status.message,
                resultTimeMs
            )
        } else {
            logger.info(
                "[{}] {}{} time={}ms ex={}",
                traceId.id,
                addSpace(EX_PREFIX, traceId.level),
                status.message,
                resultTimeMs,
                e.toString()
            )
        }

        releaseTraceId()
    }

    private fun releaseTraceId() {
        if (traceIdHolder!!.isFirstLevel()) {
            traceIdHolder = null
        } else {
            traceIdHolder = traceIdHolder!!.createPreviousId()
        }
    }

    private fun addSpace(prefix: String, level: Int): String {
        val sb = StringBuilder()
        for (i in 0 until level) {
            sb.append(if (i == level - 1) "|$prefix" else "|   ")
        }
        return sb.toString()
    }

    companion object {
        private const val START_PREFIX = "-->"
        private const val COMPLETE_PREFIX = "<--"
        private const val EX_PREFIX = "<X-"
    }
}