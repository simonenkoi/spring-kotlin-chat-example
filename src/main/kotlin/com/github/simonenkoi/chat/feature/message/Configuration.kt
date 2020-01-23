package com.github.simonenkoi.chat.feature.message

import com.github.simonenkoi.chat.feature.message.controller.routes
import com.github.simonenkoi.chat.feature.message.controller.MessageHandler
import com.github.simonenkoi.chat.feature.message.repository.MessageMongoRepository
import com.github.simonenkoi.chat.feature.message.service.MessageConverter
import com.github.simonenkoi.chat.feature.message.service.MessageService
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.fu.kofu.configuration
import org.springframework.fu.kofu.mongo.reactiveMongodb

val messageConfig = configuration {
    beans {
        bean<MessageMongoRepository>()
        bean<MessageService>()
        bean<MessageConverter>()
        bean<MessageHandler>()
        bean(::routes)
    }
    listener<ApplicationReadyEvent> {
        ref<MessageMongoRepository>().init()
    }
}