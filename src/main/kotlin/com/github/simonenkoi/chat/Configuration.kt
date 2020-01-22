package com.github.simonenkoi.chat

import org.springframework.fu.kofu.configuration
import org.springframework.fu.kofu.mongo.reactiveMongodb
import org.springframework.fu.kofu.webflux.webFlux
import java.util.logging.Handler

val dataConfig = configuration {
    beans {
        bean<MessageMongoRepository>()
    }
    reactiveMongodb {
        uri = "mongodb://localhost:27017/chat"
    }
}

val webConfig = configuration {
    beans {
        bean<ChatMessageHandler>()
        bean(::routes)
    }
    webFlux {
        port = 8080
        codecs {
            string()
            jackson()
        }
    }
}