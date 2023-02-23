package com.example.redisspring.flb.weather.city.client

import com.example.redisspring.flb.weather.city.dto.City

import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class CityClient {
    val webClient = WebClient.builder().baseUrl("http://localhost:3030/open-city-api/").build()

    fun getCity(zipCode: String): Mono<City> {
        return this.webClient.get().uri(zipCode).retrieve().bodyToMono(City::class.java)
    }

    fun getAll(): Flux<City> {
        return this.webClient.get().retrieve().bodyToFlux(City::class.java)
    }
}