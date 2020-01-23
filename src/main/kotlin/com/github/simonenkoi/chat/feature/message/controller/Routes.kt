package com.github.simonenkoi.chat.feature.message.controller

import org.springframework.web.reactive.function.server.coRouter

fun routes(messageHandler: MessageHandler) = coRouter {
    GET("/", messageHandler::listApi)
    POST("/", messageHandler::listView)
}