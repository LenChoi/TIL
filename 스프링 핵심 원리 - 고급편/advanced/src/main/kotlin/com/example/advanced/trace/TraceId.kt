package com.example.advanced.trace

import java.util.*

class TraceId(
    var id: String = UUID.randomUUID().toString().substring(0, 8),
    var level: Int = 0
) {
    fun createNextId(): TraceId {
        return TraceId(id, level + 1)
    }

    fun createPreviousId(): TraceId {
        return TraceId(id, level - 1)
    }

    fun isFirstLevel(): Boolean {
        return level == 0
    }
}

