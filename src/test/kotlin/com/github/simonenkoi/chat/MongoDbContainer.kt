package com.github.simonenkoi.chat

import org.testcontainers.containers.GenericContainer

class MongoDbContainer constructor(image: String = DEFAULT_IMAGE_AND_TAG) : GenericContainer<MongoDbContainer>(image) {

    companion object {
        const val MONGODB_PORT: Int = 27017
        const val DEFAULT_IMAGE_AND_TAG: String = "mongo:4.2"
    }

    init {
        addFixedExposedPort(MONGODB_PORT, MONGODB_PORT)
    }
}