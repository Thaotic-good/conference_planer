package org.example.program

import jakarta.persistence.CollectionTable
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Column
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.MapKeyColumn
import jakarta.persistence.MapKeyEnumerated
import jakarta.persistence.Table
import org.example.event.EventEntity
import org.example.venue.EquipmentType
import org.springframework.data.jpa.repository.JpaRepository
import java.time.Instant
import java.util.UUID

@Entity
@Table(name = "program_sessions")
class ProgramSessionEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    var id: UUID? = null,

    //    session_id      equipment_type    quantity
    //  ━━━━━━━━━━━━━━  ━━━━━━━━━━━━━━━━  ━━━━━━━━━━
    //   session UUID    MICROPHONE               2
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
        name = "session_required_equipment",
        joinColumns = [JoinColumn(name = "session_id")]
    )
    @MapKeyColumn(name = "equipment_type") // “Store the map key in the database column named equipment_type.”
    @MapKeyEnumerated(EnumType.STRING) // “Because the key is an enum, convert it to its text name, such as MICROPHONE.”
    @Column(name = "quantity", nullable = false)
    var requiredEquipment: MutableMap<EquipmentType, Int> = mutableMapOf(),

    @Column(name = "session_format", nullable = false)
    @Enumerated(EnumType.STRING)
    var sessionFormat: SessionFormat,

    @Column(name = "title", nullable = false)
    var title: String,

    @Column(name = "description", nullable = true)
    var description: String? = null,

    @Column(name = "minimal_required_capacity", nullable = false)
    var minimalRequiredCapacity: Int,

    @ManyToOne(fetch = FetchType.LAZY, optional = false) // many sessions can belong to one event day
    @JoinColumn(name = "event_id", nullable = false, updatable = false)
    var event: EventEntity,

    @Column(name = "starts_at", nullable = false)
    var startsAt: Instant,

    @Column(name = "ends_at", nullable = false)
    var endsAt: Instant,

    @Column(name = "setup_minutes", nullable = true)
    var setupMinutes: Int? = null,

    @Column(name = "teardown_minutes", nullable = true)
    var teardownMinutes: Int? = null,

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    var status: ProgramSessionStatus = ProgramSessionStatus.DRAFT,

    @Column(name = "updated_at", nullable = false)
    var updatedAt: Instant,

    @Column(name = "created_at", updatable = false, nullable = false)
    var createdAt: Instant,

    // todo: assign shifts to program session
    // todo: assign session participant to program session
    // todo: RoomEntity gets a ProgramSession assigned
) {
    fun toDomain() : ProgramSessionDomain {
        return ProgramSessionDomain(
            id = requireNotNull(id) {
                "A persisted program session must have an ID"
            },
            requiredEquipment = requiredEquipment,
            sessionFormat = sessionFormat,
            title = title,
            description = description,
            minimalRequiredCapacity = minimalRequiredCapacity,
            event = event,
            startsAt = startsAt,
            endsAt = endsAt,
            setupMinutes = setupMinutes,
            teardownMinutes = teardownMinutes,
            status = status,
            createdAt = createdAt,
            updatedAt = updatedAt,
        )
    }
}

enum class SessionFormat {
    PANEL_DISCUSSION,
    PRESENTATION,
    WORKSHOP,
    CULTURE_PROGRAMME,
    CEREMONY,
    BREAK,
}

enum class ProgramSessionStatus {
    DRAFT,
    CONFIRMED,
    DONE,
    CANCELED
}

interface ProgramSessionRepository : JpaRepository<ProgramSessionEntity, UUID>
