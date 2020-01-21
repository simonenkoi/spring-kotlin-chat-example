package com.github.simonenkoi.chat

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.awaitBody
import org.springframework.web.reactive.function.server.bodyAndAwait
import org.springframework.web.reactive.function.server.buildAndAwait
import org.springframework.web.reactive.function.server.sse

class ChatMessageHandler(
    private val repository: MessageRepository
) {

    @ExperimentalCoroutinesApi
    suspend fun listApi(request: ServerRequest): ServerResponse {
        val findAll: Flow<Message> = findAll()
        return ServerResponse
            .ok()
            .sse()
            .bodyAndAwait(findAll)
    }

    @ExperimentalCoroutinesApi
    private suspend fun findAll(): Flow<Message> = flow {
        val streamAll = repository.streamAll()
        if (streamAll.count() == 0) {
            delay(1_000)
            findAll()
        }
        streamAll.onEach { emit(it) }
    }


    suspend fun listView(request: ServerRequest): ServerResponse {
        val message = request.awaitBody<Message>()
        repository.save(message)
        return ServerResponse.accepted().buildAndAwait()
    }

}