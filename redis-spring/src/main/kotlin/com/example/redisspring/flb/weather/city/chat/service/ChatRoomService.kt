package com.example.redisspring.flb.weather.city.chat.service

import org.redisson.api.RedissonReactiveClient
import org.redisson.client.codec.StringCodec
import org.springframework.stereotype.Service
import org.springframework.web.reactive.socket.WebSocketHandler
import org.springframework.web.reactive.socket.WebSocketMessage
import org.springframework.web.reactive.socket.WebSocketSession
import org.springframework.web.util.UriComponentsBuilder
import reactor.core.publisher.Mono

@Service
class ChatRoomService(
    private val client: RedissonReactiveClient
) : WebSocketHandler {
    override fun handle(session: WebSocketSession): Mono<Void> {
        val room = getChatRoomName(session)

        val topic = this.client.getTopic(room, StringCodec.INSTANCE)

        // subscribe
        session.receive().map(WebSocketMessage::getPayloadAsText)
            .flatMap(topic::publish)
            .doOnError(::println)
            .doFinally { s -> println("Subscriber finally $s") }
            .subscribe()

        // publisher
        val flux = topic.getMessages(String::class.java)
            .map(session::textMessage)
            .doOnError(::println)
            .doFinally { s -> println("Publisher finally $s") }

        return session.send(flux)
    }

    private fun getChatRoomName(socketSession: WebSocketSession): String {
        val uri = socketSession.handshakeInfo.uri

        return UriComponentsBuilder.fromUri(uri)
            .build()
            .queryParams.toSingleValueMap().getOrDefault("room", "default")
    }
}