package com.github.simonenkoi.chat

import com.github.simonenkoi.chat.config.dataConfig
import com.github.simonenkoi.chat.config.webConfig
import com.github.simonenkoi.chat.feature.message.messageConfig
import org.springframework.boot.WebApplicationType
import org.springframework.fu.kofu.application

val app = application(WebApplicationType.REACTIVE) {
    enable(dataConfig)
    enable(messageConfig)
    enable(webConfig)
}

fun main() {
    app.run()
}
