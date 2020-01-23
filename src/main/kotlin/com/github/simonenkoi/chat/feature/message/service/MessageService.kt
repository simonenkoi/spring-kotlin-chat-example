package com.github.simonenkoi.chat.feature.message.service

import com.github.simonenkoi.chat.feature.message.controller.Message
import com.github.simonenkoi.chat.feature.message.repository.MessageDocument
import com.github.simonenkoi.chat.feature.message.repository.MessageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MessageService(
    private val repository: MessageRepository,
    private val converter: MessageConverter
) {
    suspend fun streamAll(): Flow<Message> {
        return repository.streamAll()
            .map { converter.toDto(it) }
    }

    suspend fun save(message: Message): MessageDocument? {
        return repository.save(converter.toModel(message))
    }

}