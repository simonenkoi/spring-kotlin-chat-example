package com.github.simonenkoi.chat

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.data.mongodb.core.CollectionOptions
import org.springframework.data.mongodb.core.ReactiveMongoOperations
import org.springframework.data.mongodb.core.collectionExists
import org.springframework.data.mongodb.core.createCollection
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.tail
import reactor.core.publisher.Mono

class MessageMongoRepository(private val mongo: ReactiveMongoOperations) : MessageRepository {

    fun init() {
        mongo.collectionExists<Message>()
            .flatMap {
                if (it)
                    Mono.empty()
                else
                    mongo.createCollection<Message>(CollectionOptions.empty().capped().size(100_000))
            }
            .subscribe()
    }

    override suspend fun streamAll(): Flow<Message> = mongo.tail<Message>(Query()).asFlow()
    override suspend fun save(message: Message): Message? = mongo.save(message).awaitSingle()

}
