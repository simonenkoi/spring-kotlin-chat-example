package com.github.simonenkoi.chat.feature.message.repository

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "messages")
data class MessageDocument(
    @Id val id: String? = null,
    val text: String
)