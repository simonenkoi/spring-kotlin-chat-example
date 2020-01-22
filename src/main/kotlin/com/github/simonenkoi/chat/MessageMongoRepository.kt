package com.github.simonenkoi.chat

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.data.mongodb.core.ReactiveMongoOperations
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.tail

class MessageMongoRepository(private val mongo: ReactiveMongoOperations) : MessageRepository {

    override suspend fun streamAll(): Flow<Message> {
        return mongo.tail<Message>(Query())
            .asFlow()
    }

    override suspend fun save(message: Message): Message? {
        return mongo.save(message).awaitSingle()
    }

}