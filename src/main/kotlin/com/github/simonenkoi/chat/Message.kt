package com.github.simonenkoi.chat

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "messages")
data class Message(
    @Id val login: String,
    val text: String
)