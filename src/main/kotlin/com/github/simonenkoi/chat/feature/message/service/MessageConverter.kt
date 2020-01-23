package com.github.simonenkoi.chat.feature.message.service

import com.github.simonenkoi.chat.feature.message.controller.Message
import com.github.simonenkoi.chat.feature.message.repository.MessageDocument

class MessageConverter {

    fun toDto(messageDocument: MessageDocument): Message = Message(text = messageDocument.text)

    fun toModel(message: Message): MessageDocument = MessageDocument(text = message.text)

}