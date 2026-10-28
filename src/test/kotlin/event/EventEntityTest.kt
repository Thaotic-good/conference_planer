package org.example.event

import org.junit.jupiter.api.Test
import java.time.ZoneId
import java.util.UUID
import kotlin.test.assertEquals

class EventEntityTest {
    @Test
    fun `it maps persistence fields into the domain model`() {
        val id = UUID.fromString("123e4567-e89b-12d3-a456-426614174000")
        val startsAt = java.time.Instant.parse("2026-10-15T09:00:00Z")
        val endsAt = java.time.Instant.parse("2026-10-15T17:00:00Z")
        val createdAt = java.time.Instant.parse("2026-01-01T12:00:00Z")
        val updatedAt = java.time.Instant.parse("2026-02-01T12:00:00Z")

        val entity = EventEntity(
            id = UUID.fromString("123e4567-e89b-12d3-a456-426614174000"),
            name = "RCOY Europe 2026",
            countryCode = "CZ",
            timeZoneId = "Europe/Prague",
            startsAt = startsAt,
            endsAt = endsAt,
            eventStatus = EventStatus.DRAFT,
            createdAt = createdAt,
            updatedAt = updatedAt,
        )

        val domain = entity.toDomain()

        assertEquals(id, domain.id)
        assertEquals("RCOY Europe 2026", domain.name)
        assertEquals("CZ", domain.countryCode)
        assertEquals(ZoneId.of("Europe/Prague"), domain.timeZone)
        assertEquals(startsAt, domain.startsAt)
        assertEquals(endsAt, domain.endsAt)
        assertEquals(EventStatus.DRAFT, domain.eventStatus)
        assertEquals(createdAt, domain.createdAt)
        assertEquals(updatedAt, domain.updatedAt)
    }
}
