package com.github.simonenkoi.chat

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringKotlinChatExampleApplication

fun main(args: Array<String>) {
    runApplication<SpringKotlinChatExampleApplication>(*args)
}
