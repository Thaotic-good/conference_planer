package org.example.task

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
import jakarta.persistence.Table
import org.example.people.EventParticipantEntity
import org.example.staffing.AssigmentCompletionStatus
import org.example.staffing.PriorityStatus
import org.example.staffing.ShiftCompletionStatus
import java.util.UUID

@Entity
@Table(name = "tasks")
class TaskEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, insertable = false, updatable = false)
    var id: UUID? = null,

    @Column(name = "team_id", nullable = false)
    var team_id: String? = null,

    @Column(name = "room_id")
    var room_id: String? = null,

    @Column(name = "program_session_id")
    var program_session_id: String? = null,

    @Column(name = "title", nullable = false)
    var title: String? = null,

    @Column(name = "instructions", nullable = false)
    var instructions: String? = null,

    @Column(name = "required_staff", nullable = false)
    var required_staff: Int? = null,

    @Column(name = "priority", nullable = false)
    @Enumerated(EnumType.STRING)
    var priority: PriorityStatus? = PriorityStatus.NORMAL,

    @Column(name = "completion_status", nullable = false)
    @Enumerated(EnumType.STRING)
    var completion_status: ShiftCompletionStatus? = ShiftCompletionStatus.OPEN,

    @ManyToOne
    @JoinColumn(name = "event_participant_id")
    var created_by: EventParticipantEntity? = null,
)

@Entity
@Table(name = "task_assignments")
class TaskAssigmentEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, insertable = false, updatable = false)
    var id: UUID? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignee_id", nullable = false)
    var assignee_id: EventParticipantEntity? = null,

    @Column(name = "completion_status")
    @Enumerated(EnumType.STRING)
    var completion_status: AssigmentCompletionStatus = AssigmentCompletionStatus.OFFERED,
)