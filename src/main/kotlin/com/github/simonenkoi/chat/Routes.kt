package com.github.simonenkoi.chat

import org.springframework.web.reactive.function.server.coRouter

fun routes(chatMessageHandler: ChatMessageHandler) = coRouter {
    GET("/", chatMessageHandler::listApi)
    POST("/", chatMessageHandler::listView)
}