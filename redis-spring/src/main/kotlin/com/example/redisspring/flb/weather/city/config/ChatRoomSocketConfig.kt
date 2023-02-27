package com.example.redisspring.flb.weather.city.config

import com.example.redisspring.flb.weather.city.chat.service.ChatRoomService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.HandlerMapping
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping
import org.springframework.web.reactive.socket.WebSocketHandler

@Configuration
class ChatRoomSocketConfig(
    private val chatRoomService: ChatRoomService
) {

    @Bean
    fun handlerMapping(): HandlerMapping {
        val map = mapOf<String, WebSocketHandler>(Pair("/chat", chatRoomService))

        return SimpleUrlHandlerMapping(map, -1)
    }
}