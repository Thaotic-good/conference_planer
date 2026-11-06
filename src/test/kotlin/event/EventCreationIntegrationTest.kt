package org.example.event

import com.jayway.jsonpath.JsonPath
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.postgresql.PostgreSQLContainer
import java.time.Instant
import java.util.UUID
import kotlin.test.assertEquals

@Testcontainers
@SpringBootTest
class EventCreationIntegrationTest @Autowired constructor(
    private val applicationContext: WebApplicationContext,
    private val eventRepository: EventRepository,
) {
    private lateinit var mockMvc: MockMvc //

    @BeforeEach
    fun setUpMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext).build()
    }

    @Test
    fun `POST events persists the created event`() {
        val result = mockMvc.post("/events") {
            contentType = MediaType.APPLICATION_JSON
            content = """
                {
                  "name": "RCOY Europe 2026",
                  "countryCode": "cz",
                  "timeZoneId": "Europe/Prague",
                  "startsAt": "2026-07-15T09:00:00",
                  "endsAt": "2026-07-15T17:00:00"
                }
            """.trimIndent()
        }.andExpect {
            status { isCreated() }
            content { contentType(MediaType.APPLICATION_JSON) }
            jsonPath("$.id") { isNotEmpty() }
            jsonPath("$.eventStatus") { value("DRAFT") }
        }.andReturn()

        val eventId = UUID.fromString(
            JsonPath.read(result.response.contentAsString, "$.id"),
        )
        val persistedEvent = eventRepository.findById(eventId).orElseThrow()

        assertEquals("RCOY Europe 2026", persistedEvent.name)
        assertEquals("CZ", persistedEvent.countryCode)
        assertEquals("Europe/Prague", persistedEvent.timeZoneId)
        assertEquals(Instant.parse("2026-07-15T07:00:00Z"), persistedEvent.startsAt)
        assertEquals(Instant.parse("2026-07-15T15:00:00Z"), persistedEvent.endsAt)
        assertEquals(EventStatus.DRAFT, persistedEvent.eventStatus)
    }

    companion object {
        @Container
        @JvmStatic
        private val postgresContainer = PostgreSQLContainer("postgres:18-alpine")

        @DynamicPropertySource
        @JvmStatic
        fun databaseProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", postgresContainer::getJdbcUrl)
            registry.add("spring.datasource.username", postgresContainer::getUsername)
            registry.add("spring.datasource.password", postgresContainer::getPassword)
        }
    }
}
