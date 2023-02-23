package com.example.redisspring.flb.weather.controller

import com.example.redisspring.flb.weather.service.WeatherService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/weather")
class WeatherController(
    private val weatherService: WeatherService
) {

    @GetMapping("/{zip}")
    fun getWeather(@PathVariable zip: Int): Mono<Int> {
        return Mono.fromSupplier { this.weatherService.getInfo(zip) }
    }
}