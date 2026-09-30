package org.example.people

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToMany
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import org.example.event.EventEntity
import org.example.program.ProgramSessionEntity
import org.example.staffing.ShiftAssigmentEntity
import org.example.staffing.ShiftEntity
import org.example.staffing.TeamMembershipEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

@Entity
@Table(name = "event_participants")
// add unique constraint in Flyway ["event_id", "person_id"] as in
    //(event-2026, alex)
    //(event-2027, alex)
// The JPA annotation documents the model and keeps it aligned with the schema. The database constraint is the final protection.
class EventParticipantEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    var id: UUID? = null,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "event_id",
        nullable = false,
        updatable = false
    )
    var event: EventEntity,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "person_id",
        nullable = false,
        updatable = false)
    var person: PersonEntity,

    @Column(name= "participation_status", nullable = false)
    @Enumerated(EnumType.STRING)
    var participation_status: ParticipationStatus? = ParticipationStatus.INVITED,

    @Column(nullable = false)
    var maximum_hours_per_day: Int? = null,

    @Column(nullable = false)
    var emergency_contact: String? = null,

    @Column(nullable = false)
    var notes: String? = null,

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "created_by")
    val created_shifts: MutableList<ShiftEntity> = mutableListOf(),

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "assignee_id")
    val assigned_to_shifts: MutableList<ShiftAssigmentEntity> = mutableListOf(),
)

enum class ParticipationStatus {
    INVITED,
    CONFIRMED,
    DECLINED,
    INACTIVE
}

@Entity
@Table(name = "session_participation")
class SessionParticipationEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, insertable = false, updatable = false)
    var id: UUID? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_participant")
    var event_participant: EventParticipantEntity? = null,

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "sessions")
    var sessions: ProgramSessionEntity? = null,
)

//| Participant           | Team memberships | Session participations |
//| Regular organizer     | 1+ | 0  |
//| Speaker               | 0  | 1+ |
//| Organizer and speaker | 1+ | 1+ |
//| Venue technician      | 0  | 1+ |

@Entity
@Table(name = "session_participation_role")
class SessionParticipationRoleEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, insertable = false, updatable = false)
    var id: UUID? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_participation")
    var session_participation: SessionParticipationEntity? = null,

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    var role: ParticipantRoleType? = null,
)

enum class ParticipantRoleType {
    SPEAKER,
    VENUE_TECHNICIAN,
    PRESS,
    VENDOR,
    ATTENDEE,
}

interface EventParticipantRepository : JpaRepository<EventParticipantEntity, UUID> {

    fun findByEventIdAndParticipantId(
        eventId: UUID,
        participantId: UUID
    ): EventParticipantEntity?

    fun existsByEventIdAndParticipantId(
        eventId: UUID,
        participantId: UUID
    ): Boolean

    fun findAllByEventId(
        eventId: UUID
    ): List<EventParticipantEntity>
}