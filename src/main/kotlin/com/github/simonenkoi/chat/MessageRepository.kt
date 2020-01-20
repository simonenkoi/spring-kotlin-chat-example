package com.github.simonenkoi.chat

import kotlinx.coroutines.flow.Flow
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.data.mongodb.repository.Tailable

interface MessageRepository {

    suspend fun streamAll(): Flow<Message>

    suspend fun save(message: Message): Message?

}