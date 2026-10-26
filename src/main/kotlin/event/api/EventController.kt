package org.example.event.api

import io.swagger.v3.oas.annotations.Operation
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import org.example.event.CreateEventCommand
import org.example.event.EventDomain
import org.example.event.EventService
import org.example.event.EventStatus
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Locale
import java.util.UUID

@RestController
class EventController(
    private val eventService: EventService,
) {
    @GetMapping("/events")
    fun getAllEvents(): List<String> = eventService.getAll()

    @GetMapping("/events/{id}")
    fun getEventById(@PathVariable id: UUID): EventDto = eventService.getById(id).toDto()

    @PostMapping("/events")
    fun create(@Valid @RequestBody request: CreateEventRequest): ResponseEntity<EventResponse> {
        val domain = eventService.create(request.toCommand())
        return ResponseEntity.status(HttpStatus.CREATED).body(domain.toResponse())
    }
}

data class CreateEventRequest(
    @field:NotBlank
    val name: String,
    @field:Pattern(regexp = "[A-Za-z]{2}")
    val countryCode: String,
    @field:NotBlank
    val timeZoneId: String,
    val startsAt: LocalDateTime,
    val endsAt: LocalDateTime,
)

data class EventResponse(
    val id: UUID,
    val name: String,
    val countryCode: String,
    val timeZoneId: String,
    val startsAt: Instant,
    val endsAt: Instant,
    val eventStatus: EventStatus,
    val createdAt: Instant,
    val updatedAt: Instant,
)

private fun CreateEventRequest.toCommand(): CreateEventCommand = CreateEventCommand(
    name = name,
    countryCode = countryCode.trim().uppercase(Locale.ROOT),
    timeZone = ZoneId.of(timeZoneId),
    startsAt = startsAt,
    endsAt = endsAt,
)

private fun EventDomain.toResponse(): EventResponse = EventResponse(
    id = id,
    name = name,
    countryCode = countryCode,
    timeZoneId = timeZone.id,
    startsAt = startsAt,
    endsAt = endsAt,
    eventStatus = eventStatus,
    createdAt = createdAt,
    updatedAt = updatedAt,
)

data class EventDto(
    val name: String,
    val countryCode: String,
    val timeZoneId: String,
    val startsAt: Instant,
    val endsAt: Instant,
    val eventStatus: EventStatus,
    val createdAt: Instant,
    val updatedAt: Instant,
)
