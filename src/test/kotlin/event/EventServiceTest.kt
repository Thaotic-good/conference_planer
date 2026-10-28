package org.example.event

import org.example.event.domain.InvalidCountryTimeZoneException
import org.example.event.domain.InvalidEventLocalDateTimeException
import org.example.event.domain.InvalidEventTimeRangeException
import org.example.event.timezone.TimeZoneCatalog
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito
import java.time.Clock
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import kotlin.test.assertEquals

class EventServiceTest {
    private val repository = Mockito.mock(EventRepository::class.java)
    private val clock = Clock.fixed(Instant.parse("2026-10-15T08:00:00Z"), ZoneOffset.UTC)
    private val catalog = StubTimeZoneCatalog(setOf("CZ" to "Europe/Prague"))
    private val service = EventService(repository, catalog, clock)

    @Test
    fun `creates an event by resolving Prague local times to instants`() {
        Mockito.`when`(repository.save(any(EventEntity::class.java))).thenAnswer { invocation ->
            invocation.getArgument<EventEntity>(0).apply {
                id = java.util.UUID.fromString("123e4567-e89b-12d3-a456-426614174000")
            }
        }

        val created = service.create(validCommand())

        assertEquals(Instant.parse("2026-07-15T07:00:00Z"), created.startsAt)
        assertEquals(Instant.parse("2026-07-15T15:00:00Z"), created.endsAt)
        assertEquals("CZ", created.countryCode)
        assertEquals(ZoneId.of("Europe/Prague"), created.timeZone)
        assertEquals(Instant.parse("2026-10-15T08:00:00Z"), created.createdAt)
    }

    @Test
    fun `rejects a time zone that does not belong to the country`() {
        val exception = assertThrows<InvalidCountryTimeZoneException> {
            service.create(validCommand().copy(timeZone = ZoneId.of("America/New_York")))
        }

        assertEquals(
            "Time zone 'America/New_York' is not available for country 'CZ'.",
            exception.message,
        )
        Mockito.verifyNoInteractions(repository)
    }

    @Test
    fun `rejects a local time in a daylight-saving gap`() {
        assertThrows<InvalidEventLocalDateTimeException> {
            service.create(
                validCommand().copy(startsAt = LocalDateTime.parse("2026-03-29T02:30:00")),
            )
        }
        Mockito.verifyNoInteractions(repository)
    }

    @Test
    fun `rejects an ambiguous local time in a daylight-saving overlap`() {
        assertThrows<InvalidEventLocalDateTimeException> {
            service.create(
                validCommand().copy(startsAt = LocalDateTime.parse("2026-10-25T02:30:00")),
            )
        }
        Mockito.verifyNoInteractions(repository)
    }

    @Test
    fun `rejects an end time that is not after the start time`() {
        assertThrows<InvalidEventTimeRangeException> {
            service.create(
                validCommand().copy(endsAt = LocalDateTime.parse("2026-07-15T09:00:00")),
            )
        }
        Mockito.verifyNoInteractions(repository)
    }

    private fun validCommand() = CreateEventCommand(
        name = "RCOY Europe 2026",
        countryCode = "CZ",
        timeZone = ZoneId.of("Europe/Prague"),
        startsAt = LocalDateTime.parse("2026-07-15T09:00:00"),
        endsAt = LocalDateTime.parse("2026-07-15T17:00:00"),
    )
}

private class StubTimeZoneCatalog(
    private val supportedPairs: Set<Pair<String, String>>,
) : TimeZoneCatalog {
    override fun findTimeZoneIds(countryCode: String): List<String> = supportedPairs
        .filter { it.first == countryCode }
        .map { it.second }

    override fun supports(countryCode: String, zoneId: ZoneId): Boolean =
        countryCode to zoneId.id in supportedPairs
}
