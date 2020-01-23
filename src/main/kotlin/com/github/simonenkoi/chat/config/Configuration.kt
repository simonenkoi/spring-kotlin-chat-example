package com.github.simonenkoi.chat.config

import org.springframework.fu.kofu.configuration
import org.springframework.fu.kofu.mongo.reactiveMongodb
import org.springframework.fu.kofu.webflux.webFlux

val dataConfig = configuration {
    reactiveMongodb {
        uri = "mongodb://localhost:27017/chat"
    }
}

val webConfig = configuration {
    webFlux {
        port = 8080
        codecs {
            string()
            jackson()
        }
    }
}