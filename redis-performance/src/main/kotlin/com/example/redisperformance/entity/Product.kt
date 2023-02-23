package com.example.redisperformance.entity

import org.springframework.data.annotation.Id
import java.math.BigDecimal

class Product (
    @Id
    var id: Long?,
    var description: String,
    var price: BigDecimal
)