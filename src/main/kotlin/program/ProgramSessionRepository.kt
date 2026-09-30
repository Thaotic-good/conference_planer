package org.example.program

import jakarta.persistence.CollectionTable
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import org.example.venue.RoomEntity
import jakarta.persistence.Column
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.example.event.EventEntity
import org.example.people.EventParticipantEntity
import org.example.venue.EquipmentType
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

@Entity
@Table(name = "program_sessions")
class ProgramSessionEntity(
    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false, nullable = false)
    var id: UUID? = null,

//    @Column(nullable = false)
//    @Enumerated(EnumType.STRING)
//    var session_type: SessionType? = null,

    @Column(nullable = false)
    var title: String,

    @Column(nullable = false)
    var format: String,

    @Column(nullable = false)
    var description: String? = null,

    @Column(nullable = false)
    var capacityRequired: Int? = null,

    @ManyToOne(fetch = FetchType.LAZY, optional = false) // many sessions can belong to one event day
    @JoinColumn(name = "event_id", nullable = false)
    var event_id: EventEntity? = null,

    @ManyToOne(fetch = FetchType.LAZY, optional = false) // many sessions can belong to one room
    @JoinColumn(name = "room_id", nullable = false)
    var room_id: RoomEntity? = null,

    @Column(name = "starts_at", insertable = false, updatable = false, nullable = false)
    var starts_at: java.time.Instant,

    @Column(name = "ends_at", insertable = false, updatable = false, nullable = false)
    var ends_at: java.time.Instant,

    @Column(nullable = true)
    var setup_minutes: Int? = null,

    @Column(nullable = true)
    var teardown_minutes: Int? = null,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var status: Status? = null,

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
        name = "session_required_equipment",
        joinColumns = [JoinColumn(name = "session_id")]
    )
    @Column(name = "required_equipment")
    @Enumerated(EnumType.STRING)
    val required_equipment: MutableSet<EquipmentType> = mutableSetOf(),

    @Column(name = "created_at", insertable = false, updatable = false, nullable = false)
    var created_at: java.time.Instant, // Recommended: Standardizes to UTC in DB (TIMESTAMPTZ in PostgreSQL)

    @Column(name = "updated_at", insertable = false, updatable = false, nullable = false)
    var updated_at: java.time.Instant,
)

//enum class SessionType {
//    PANEL_DISCUSSION,
//    PRESENTATION,
//    WORKSHOP,
//    CULTURE_PROGRAMME,
//    BREAK,
//}

enum class Status {
    DRAFT,
    CONFIRMED,
    DONE,
    CANCELED
}

interface ProgramSessionRepository : JpaRepository<ProgramSessionEntity, UUID> {

    fun findAllByEventId(
        eventId: UUID
    ): List<EventParticipantEntity>

    fun findByEventIdAndRoomId(
        eventId: UUID,
        roomId: UUID
    ): List<EventParticipantEntity>
}