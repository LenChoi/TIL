package com.example.redisspring.flb.weather.city.service

import com.example.redisspring.flb.weather.city.client.CityClient
import com.example.redisspring.flb.weather.city.dto.City
import org.redisson.api.RMapCacheReactive
import org.redisson.api.RMapReactive
import org.redisson.api.RedissonReactiveClient
import org.redisson.codec.TypedJsonJacksonCodec
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.util.concurrent.TimeUnit
import java.util.function.Function
import java.util.stream.Collectors

@Service
class CityService(
    private val client: RedissonReactiveClient,
    private val cityClient: CityClient
) {
//    val cityMap: RMapCacheReactive<String, City> =
//        client.getMapCache("city", TypedJsonJacksonCodec(String::class.java, City::class.java))

    val cityMap: RMapReactive<String, City> =
        client.getMap("city", TypedJsonJacksonCodec(String::class.java, City::class.java))

//    fun getCity(zipCode: String): Mono<City> {
//        return this.cityMap.get(zipCode)
//            .switchIfEmpty(
//                cityClient.getCity(zipCode)
//                    .flatMap { c -> this.cityMap.fastPut(zipCode, c, 10, TimeUnit.SECONDS).thenReturn(c) }) // 10 초 후 자동 삭제
//    }

    fun getCity(zipCode: String): Mono<City> {
        return this.cityMap.get(zipCode).onErrorResume { this.cityClient.getCity(zipCode) }
    }

    @Scheduled(fixedRate = 10_000)
    fun updateCity() {
        cityClient.getAll()
            .collectList()
            .map { list -> list.stream().collect(Collectors.toMap(City::zip, Function.identity())) }
            .flatMap(cityMap::putAll).subscribe()
    }
}