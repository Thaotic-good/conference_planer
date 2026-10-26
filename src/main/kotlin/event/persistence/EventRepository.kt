package org.example.event

import jakarta.persistence.*
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

// represents a database row
// contains application-generated fields and JPA annotations
@Entity
@Table(name = "events")
class EventEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID) // Automatically triggers Hibernate's UUID generator
    @Column(name = "id", updatable = false, nullable = false)
    var id: UUID? = null,

    @Column(name = "name", nullable = false)
    var name: String,

    @Column(name = "country_code", nullable = false, length = 2)
    var countryCode: String,

    @Column(name = "time_zone_id", nullable = false, length = 64)
    var timeZoneId: String,

    @Column(name = "starts_at", updatable = false, nullable = false)
    var startsAt: java.time.Instant,

    @Column(name = "ends_at", updatable = false, nullable = false)
    var endsAt: java.time.Instant,

    @Column(name = "event_status", nullable = false)
    @Enumerated(EnumType.STRING)
    var eventStatus: EventStatus,

    // todo: when ProgramSessionEntity exists, create migration script v2
    // 'mappedBy' points to the 'session' field in ProgramSessionEntity (does NOT create a new FK column)
//    @OneToMany(mappedBy = "event", fetch = FetchType.LAZY)
//    var programSessions: MutableList<ProgramSessionEntity> = mutableListOf(),

    @Column(name = "created_at", updatable = false, nullable = false)
    var createdAt: java.time.Instant, // Recommended: Standardizes to UTC in DB (TIMESTAMPTZ in PostgreSQL)

    @Column(name = "updated_at", nullable = false)
    var updatedAt: java.time.Instant,
) {
    fun toDomain(): EventDomain {
        return EventDomain(
            id = requireNotNull(id) {
                "A persisted event must have an ID"
            },
            name = name,
            countryCode = countryCode,
            timeZone = java.time.ZoneId.of(timeZoneId),
            startsAt = startsAt,
            endsAt = endsAt,
            eventStatus = eventStatus,
//            programSessions = programSessions,
            createdAt = createdAt,
            updatedAt = updatedAt,
        )
    }
}

enum class EventStatus { // could be evaluated by backend
    DRAFT,
    CONFIRMED, // if the schedule is locked in
    CLOSED
}

interface EventRepository : JpaRepository<EventEntity, UUID> {
}
