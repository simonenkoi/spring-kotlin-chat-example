package com.github.simonenkoi.chat.feature.message.repository

import kotlinx.coroutines.flow.Flow

interface MessageRepository {

    suspend fun streamAll(): Flow<MessageDocument>

    suspend fun save(message: MessageDocument): MessageDocument?

}