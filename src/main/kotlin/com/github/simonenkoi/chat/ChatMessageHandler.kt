package com.github.simonenkoi.chat

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.awaitBody
import org.springframework.web.reactive.function.server.bodyAndAwait
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import org.springframework.web.reactive.function.server.buildAndAwait
import org.springframework.web.reactive.function.server.sse
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.net.URI

class ChatMessageHandler(
    private val repository: MessageRepository
) {

    suspend fun listApi(request: ServerRequest): ServerResponse {
        val findAll: Flow<Message> = repository.streamAll()

        return ServerResponse
            .ok()
            .sse()
            .bodyAndAwait(findAll)
    }


    suspend fun listView(request: ServerRequest): ServerResponse {
        val message = request.awaitBody<Message>()
        repository.save(message)
        return ServerResponse.accepted().buildAndAwait()
    }

}