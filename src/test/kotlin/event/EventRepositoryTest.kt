package org.example.event

import jakarta.persistence.EntityManager
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.postgresql.PostgreSQLContainer
import kotlin.test.assertEquals

@Testcontainers // generates a disposable PostgreSQL database
@DataJpaTest // starts a focused test context (JPA, Spring Data, Hibernate, Flyway, rollback transactions)
@AutoConfigureTestDatabase(
    replace = AutoConfigureTestDatabase.Replace.NONE // Keep the PostgreSQL datasource supplied by Testcontainers
) // PostgreSQL-specific behavior related to TIMESTAMPTZ, UUID, and constraints
class EventRepositoryTest @Autowired constructor(
    private val eventRepository: EventRepository, // Spring creates the generated repository object and passes it into the test constructor (no source-code implementation exists, Spring builds a proxy at runtime)
) {
    @Autowired
    private lateinit var entityManager: EntityManager

    @Test
    fun `new event saved and can be retrieved`() {
        val startsAt = java.time.Instant.parse("2026-10-15T09:00:00Z")
        val endsAt = java.time.Instant.parse("2026-10-15T17:00:00Z")
        val createdAt = java.time.Instant.parse("2026-01-01T12:00:00Z")
        val updatedAt = java.time.Instant.parse("2026-02-01T12:00:00Z")

        val entity = EventEntity(
            id = null,
            name = "RCOY Europe 2026",
            countryCode = "CZ",
            timeZoneId = "Europe/Prague",
            startsAt = startsAt,
            endsAt = endsAt,
            eventStatus = EventStatus.DRAFT,
            createdAt = createdAt,
            updatedAt = updatedAt,
        )

        val savedEntity = eventRepository.saveAndFlush(entity)
        val generatedId = requireNotNull(savedEntity.id)

        // otherwise Hibernate may return the already managed in-memory entity instead of reconstructing it from a database query.
        entityManager.clear() // Clear Hibernate’s in-memory persistence context. entity and savedEntity may represent the same in-memory managed entity

        val retrievedEntity = eventRepository.findById(generatedId)
            .orElseThrow()

        assertEquals("RCOY Europe 2026", retrievedEntity.name)
        assertEquals("CZ", retrievedEntity.countryCode)
        assertEquals("Europe/Prague", retrievedEntity.timeZoneId)
        assertEquals(startsAt, retrievedEntity.startsAt)
        assertEquals(endsAt, retrievedEntity.endsAt)
        assertEquals(EventStatus.DRAFT, retrievedEntity.eventStatus)
        assertEquals(createdAt, retrievedEntity.createdAt)
        assertEquals(updatedAt, retrievedEntity.updatedAt)
    }

    // in Kotlin, we achieve the equivalent of Java’s static methods using the companion object construct. A companion object in Kotlin allows us to define methods and properties that belong to the class itself, rather than class instances.
    companion object {
        @Container // Marks this PostgreSQL container as part of the test lifecycle. Testcontainers starts it before the test and stops it afterward.
        @JvmStatic // exposes the members as actual Java-style static members so JUnit and Spring can discover them reliably
        private val postgresContainer = PostgreSQLContainer("postgres:18-alpine")

        @DynamicPropertySource // Spring needs the dynamic configuration method before it creates the test instance and application context.
        @JvmStatic
        fun databaseProperties(registry: DynamicPropertyRegistry) { //   Supplies configuration values that aren’t known until the container starts:
            registry.add("spring.datasource.url", postgresContainer::getJdbcUrl)
            registry.add("spring.datasource.username", postgresContainer::getUsername)
            registry.add("spring.datasource.password", postgresContainer::getPassword)

        }

    }
}
