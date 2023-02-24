package com.example.redisperformance.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.math.BigDecimal

@Table
class Product(
    @Id
    var id: Long? = null,
    var description: String? = null,
    var price: BigDecimal? = null
)