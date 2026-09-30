package org.example.staffing

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import org.example.people.EventParticipantEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

@Entity
@Table(name = "shifts")
class ShiftEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, insertable = false, updatable = false)
    var id: UUID? = null,

    @Column(name="team_id", nullable = false)
    var team_id: String? = null,

    @Column(name="room_id", nullable = false)
    var room_id: String? = null,

    @Column(name="program_session_id", nullable = false)
    var program_session_id: String? = null,

    @Column(name="title", nullable = false)
    var title: String? = null,

    @Column(name="instructions", nullable = false)
    var instructions: String? = null,

    @Column(name="required_staff", nullable = false)
    var required_staff: Int? = null,

    @Column(name="starts_at", nullable = false)
    var starts_at: String? = null,

    @Column(name="ends_at", nullable = false)
    var ends_at: String? = null,

    @Column(name="priority", nullable = false)
    @Enumerated(EnumType.STRING)
    var priority: PriorityStatus? = PriorityStatus.NORMAL,

    @Column(name="completion_status", nullable = false)
    @Enumerated(EnumType.STRING)
    var completion_status: ShiftCompletionStatus? = ShiftCompletionStatus.OPEN,

    @ManyToOne
    @JoinColumn(name = "event_participant_id")
    var created_by: EventParticipantEntity? = null,

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "shift")
    val slots: MutableList<ShiftSlotEntity> = mutableListOf(),
)

enum class PriorityStatus {
    NORMAL,
    IMPORTANT,
    CRITICAL,
}

enum class ShiftCompletionStatus {
    OPEN,
    STAFFED,
    COMPLETED,
    CANCELLED,
    AT_RISK,
}

@Entity
@Table(name = "shift_slots")
class ShiftSlotEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, insertable = false, updatable = false)
    var id: UUID? = null,

    @Column(name="acceptance_status", nullable = false)
    @Enumerated(EnumType.STRING)
    var acceptance_status: AcceptanceStatus = AcceptanceStatus.UNSTAFFED,

    @ManyToOne
    @JoinColumn(name = "shift_id")
    var shift: ShiftEntity,

    @OneToOne
    @Column(name = "shift_assignment", nullable = false)
    var shift_assignment: ShiftAssigmentEntity? = null,
)

enum class AcceptanceStatus {
    UNSTAFFED,
    FILLED,
    HALF_FILLED
}

@Entity
@Table(name = "shift_assignments")
class ShiftAssigmentEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, insertable = false, updatable = false)
    var id: UUID? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignee_id", nullable = false)
    var assignee_id: TeamMembershipEntity? = null,

    @OneToOne(mappedBy = "shift_assignment")
    var shiftSlot: ShiftSlotEntity,

    @Column(name = "completion_status")
    @Enumerated(EnumType.STRING)
    var completion_status: AssigmentCompletionStatus = AssigmentCompletionStatus.OFFERED,
)

enum class AssigmentCompletionStatus {
    OFFERED,
    ACCEPTED,
//    DECLINED,
    NO_SHOW,
    COMPLETED,
}

interface ShiftRepository : JpaRepository<ShiftEntity, UUID> {

    fun findAllByEventId(
        eventId: UUID
    ): List<ShiftEntity>

    fun findAllByTeamId(
        teamId: UUID
    ): List<ShiftEntity>

    fun findAllByStatus(
        completion_status: ShiftCompletionStatus
    ): List<ShiftEntity>
}

interface ShiftSlotRepository : JpaRepository<ShiftSlotEntity, UUID> {
    fun findAllBySessionId(
        sessionId: UUID
    ): List<ShiftSlotEntity>

    fun findByShiftAcceptanceStatus(
        acceptance_status: AcceptanceStatus
    ): List<ShiftSlotEntity>
}

interface ShiftAssigmentRepository : JpaRepository<ShiftAssigmentEntity, UUID> {

    fun findAllBySessionIdAndEventId(
        sessionId: UUID,
        eventId: UUID
    ): List<ShiftAssigmentEntity>

    fun findByAssigneeId(
        assigneeId: UUID,
    ): TeamMembershipEntity

    fun findByCompletionStatus(
        completionStatus: ShiftCompletionStatus
    ): List<ShiftAssigmentEntity>
}