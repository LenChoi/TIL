package com.example.redisspring.flb.weather.city.controller

import com.example.redisspring.flb.weather.city.dto.City
import com.example.redisspring.flb.weather.city.service.CityService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/city")
class CityController(
    private val cityService: CityService
) {
//    @GetMapping("/{zipcode}")
//    fun getCity(@PathVariable zipcode: String): Mono<City> {
//        return cityService.getCity(zipcode)
//    }
}