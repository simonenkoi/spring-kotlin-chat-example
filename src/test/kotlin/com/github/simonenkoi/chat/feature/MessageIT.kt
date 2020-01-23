package com.github.simonenkoi.chat.feature

import com.github.simonenkoi.chat.MongoDbContainer
import com.github.simonenkoi.chat.app
import com.github.simonenkoi.chat.feature.message.controller.Message
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.returnResult
import org.testcontainers.containers.GenericContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import reactor.test.StepVerifier


@Testcontainers
class MessageIT {

    companion object {

        private lateinit var context: ConfigurableApplicationContext

        private val CLIENT = WebTestClient.bindToServer().baseUrl("http://localhost:8080").build()

        @Container
        private val MONGO_CONTAINER = MongoDbContainer()

        @BeforeAll
        @JvmStatic
        fun beforeAll() {
            context = app.run()
        }

        @AfterAll
        @JvmStatic
        fun afterAll() {
            context.close()
        }
    }

    @Test
    fun `endpoint should return submitted messages`() {
        createMessages("TEST1", "TEST2", "TEST3")

        val result = CLIENT.get().uri("/").accept(MediaType.TEXT_EVENT_STREAM).exchange()
            .expectStatus().is2xxSuccessful
            .returnResult<Message>()

        StepVerifier.create<Message>(result.responseBody)
            .assertNext { assertThat(it.text).isEqualTo("TEST1") }
            .assertNext { assertThat(it.text).isEqualTo("TEST2") }
            .assertNext { assertThat(it.text).isEqualTo("TEST3") }
            .thenCancel()
            .verify()

    }

    private fun createMessages(vararg text: String) {
        text.forEach {
            CLIENT.post().uri("/")
                .bodyValue(Message(text = it))
                .exchange()
        }
    }
}