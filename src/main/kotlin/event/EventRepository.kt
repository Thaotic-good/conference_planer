package org.example.event

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import org.example.program.ProgramSessionEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

@Entity
@Table(name = "events")
class EventEntity(
    @Id
    @GeneratedValue // Automatically triggers Hibernate's UUID generator
    @Column(name = "event_id", updatable = false, nullable = false)
    var event_id: UUID? = null,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var name: ConferenceDay,

    @Column(nullable = false)
    var venue_name: String,

    @Column(name = "starts_at", insertable = false, updatable = false, nullable = false)
    var starts_at: String,

    @Column(name = "ends_at", insertable = false, updatable = false, nullable = false)
    var ends_at: String,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var program_status: Status,

    // 'mappedBy' points to the 'session' field in ProgramSessionEntity (does NOT create a new FK column)
    @OneToMany(mappedBy = "program_sessions", fetch = FetchType.LAZY)
    val program_sessions: MutableList<ProgramSessionEntity> = mutableListOf(),

    @Column(name = "created_at", insertable = false, updatable = false, nullable = false)
    var created_at: java.time.Instant, // Recommended: Standardizes to UTC in DB (TIMESTAMPTZ in PostgreSQL)

    @Column(name = "updated_at", insertable = false, updatable = false, nullable = false)
    var updated_at: java.time.Instant,
)

enum class Status {
    DRAFT,
    REVIEW,
    CONFIRMED,
}

enum class ConferenceDay {
    FRIDAY,
    SATURDAY,
    SUNDAY,
}

interface EventRepository : JpaRepository<EventRepository, UUID>
