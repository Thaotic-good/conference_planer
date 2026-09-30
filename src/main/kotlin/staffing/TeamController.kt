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
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import org.example.event.EventEntity
import org.example.people.EventParticipantEntity
import java.util.UUID

@Entity
@Table(name = "teams")
class TeamEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, insertable = false, updatable = false)
    var id: UUID? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    var event: EventEntity? = null,

    @Column(name = "team_type")
    @Enumerated(EnumType.STRING)
    var team_type: TeamType? = null,

    @Column(name = "responsibilities")
    var responsibilities: String? = null,
)

enum class TeamType {
    MEDIA,
    OPERATIONS,
    PROGRAM,
    ADMIN,
    STEERING_COMMITTEE
}

@Entity
@Table(name = "team_membership")
class TeamMembershipEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, insertable = false, updatable = false)
    var id: UUID? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team")
    var team: TeamEntity? = null,

    @OneToOne
    @JoinColumn(name = "event_participant", insertable = false, updatable = false, nullable = false)
    var event_participant: EventParticipantEntity? = null,

    @Column(name = "joined_at")
    var joined_at: java.time.Instant? = null,

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    var status: MembershipStatus? = null,
)

enum class MembershipStatus {
    ACTIVE,
    INACTIVE,
}

@Entity
@Table(name = "team_membership_role")
class TeamMembershipRoleRoleEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, insertable = false, updatable = false)
    var id: UUID? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_membership")
    var team_membership: TeamMembershipEntity? = null,

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    var role: TeamRoleType? = null,
)

enum class TeamRoleType {
    TEAM_LEADER,
    TEAM_CO_LEAD,
    TEAM_MEMBER,
    TEAM_VOLUNTEER,
}