package org.example.program

import jakarta.persistence.EntityManager
import org.example.event.EventEntity
import org.example.event.EventRepository
import org.example.event.EventStatus
import org.example.venue.EquipmentType
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.postgresql.PostgreSQLContainer
import java.time.Instant
import kotlin.test.assertEquals

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProgramSessionRepositoryTest @Autowired constructor(
    private val eventRepository: EventRepository,
    private val programSessionRepository: ProgramSessionRepository,
    private val entityManager: EntityManager,
) {
    @Test
    fun `session and its equipment requirements can be persisted`() {
        val event = eventRepository.saveAndFlush(
            EventEntity(
                name = "Kotlin Conference",
                countryCode = "CZ",
                timeZoneId = "Europe/Prague",
                startsAt = Instant.parse("2027-05-10T08:00:00Z"),
                endsAt = Instant.parse("2027-05-10T18:00:00Z"),
                eventStatus = EventStatus.DRAFT,
                createdAt = Instant.parse("2026-11-19T10:00:00Z"),
                updatedAt = Instant.parse("2026-11-19T10:00:00Z"),
            )
        )

        val session = ProgramSessionEntity(
            sessionFormat = SessionFormat.WORKSHOP,
            title = "Practical Kotlin",
            description = "Build a small Spring application.",
            minimalRequiredCapacity = 20,
            event = event,
            startsAt = Instant.parse("2027-05-10T09:00:00Z"),
            endsAt = Instant.parse("2027-05-10T10:30:00Z"),
            setupMinutes = 15,
            teardownMinutes = 10,
            createdAt = Instant.parse("2026-11-19T10:00:00Z"),
            updatedAt = Instant.parse("2026-11-19T10:00:00Z"),
        ).apply {
            requiredEquipment[EquipmentType.PROJECTOR] = 1
            requiredEquipment[EquipmentType.MICROPHONE] = 2
        }

        val sessionId = requireNotNull(programSessionRepository.saveAndFlush(session).id)
        entityManager.clear()

        val retrieved = programSessionRepository.findById(sessionId).orElseThrow()

        assertEquals(SessionFormat.WORKSHOP, retrieved.sessionFormat)
        assertEquals("Practical Kotlin", retrieved.title)
        assertEquals(20, retrieved.minimalRequiredCapacity)
        assertEquals(ProgramSessionStatus.DRAFT, retrieved.status)
        assertEquals(
            mapOf(
                EquipmentType.PROJECTOR to 1,
                EquipmentType.MICROPHONE to 2,
            ),
            retrieved.requiredEquipment,
        )
        assertEquals(event.id, retrieved.event.id)
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
