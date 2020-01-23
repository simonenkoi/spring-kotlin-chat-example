package com.github.simonenkoi.chat.feature.message.controller

import com.github.simonenkoi.chat.feature.message.service.MessageService
import kotlinx.coroutines.flow.Flow
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.awaitBody
import org.springframework.web.reactive.function.server.bodyAndAwait
import org.springframework.web.reactive.function.server.buildAndAwait
import org.springframework.web.reactive.function.server.sse

class MessageHandler(private val service: MessageService) {

    suspend fun listApi(request: ServerRequest): ServerResponse {
        val findAll: Flow<Message> = service.streamAll()

        return ServerResponse
            .ok()
            .sse()
            .bodyAndAwait(findAll)
    }

    suspend fun listView(request: ServerRequest): ServerResponse {
        val message = request.awaitBody<Message>()
        service.save(message)
        return ServerResponse.accepted().buildAndAwait()
    }

}