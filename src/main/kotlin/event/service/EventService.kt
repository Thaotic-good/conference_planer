package org.example.event

import org.example.event.api.EventDto
import org.example.event.domain.InvalidCountryTimeZoneException
import org.example.event.domain.InvalidEventLocalDateTimeException
import org.example.event.domain.InvalidEventTimeRangeException
import org.example.event.timezone.TimeZoneCatalog
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.Clock
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.UUID

@Service
class EventService(
    private val eventRepository: EventRepository,
    private val timeZoneCatalog: TimeZoneCatalog,
    private val clock: Clock,
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    fun create(command: CreateEventCommand): EventDomain {
        if (!timeZoneCatalog.supports(command.countryCode, command.timeZone)) {
            throw InvalidCountryTimeZoneException(command.countryCode, command.timeZone.id)
        }

        val startsAt = command.startsAt.toUnambiguousInstant(command.timeZone, "start")
        val endsAt = command.endsAt.toUnambiguousInstant(command.timeZone, "end")
        if (!endsAt.isAfter(startsAt)) {
            throw InvalidEventTimeRangeException("Event end time must be after its start time.")
        }

        val now = Instant.now(clock)
        return eventRepository.save(command.toEntity(startsAt, endsAt, now)).toDomain()
    }

    fun getAll(): List<String> = eventRepository.findAll().map { it.name }

    fun getById(id: UUID): EventDomain = eventRepository.findById(id)
        .orElseThrow {
            logger.error("Event with ID {} was not found", id)
            IllegalArgumentException("Event with ID $id was not found")
        }
        .toDomain()

    private fun LocalDateTime.toUnambiguousInstant(zoneId: ZoneId, fieldName: String): Instant {
        val validOffsets = zoneId.rules.getValidOffsets(this)
        return when (validOffsets.size) {
            1 -> toInstant(validOffsets.single())
            0 -> throw InvalidEventLocalDateTimeException(
                "Event $fieldName time '$this' does not exist in time zone '${zoneId.id}' because of a daylight-saving transition.",
            )
            else -> throw InvalidEventLocalDateTimeException(
                "Event $fieldName time '$this' is ambiguous in time zone '${zoneId.id}' because of a daylight-saving transition.",
            )
        }
    }

    private fun CreateEventCommand.toEntity(
        startsAtInstant: Instant,
        endsAtInstant: Instant,
        now: Instant,
    ): EventEntity = EventEntity(
        name = name,
        countryCode = countryCode,
        timeZoneId = timeZone.id,
        startsAt = startsAtInstant,
        endsAt = endsAtInstant,
        eventStatus = EventStatus.DRAFT,
        createdAt = now,
        updatedAt = now,
    )
}

data class CreateEventCommand(
    val name: String,
    val countryCode: String,
    val timeZone: ZoneId,
    val startsAt: LocalDateTime,
    val endsAt: LocalDateTime,
)

data class EventDomain(
    val id: UUID,
    val name: String,
    val countryCode: String,
    val timeZone: ZoneId,
    val startsAt: Instant,
    val endsAt: Instant,
    val eventStatus: EventStatus,
    val createdAt: Instant,
    val updatedAt: Instant,
) {
    fun toDto(): EventDto = EventDto(
        name = name,
        countryCode = countryCode,
        timeZoneId = timeZone.id,
        startsAt = startsAt,
        endsAt = endsAt,
        eventStatus = eventStatus,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )
}
