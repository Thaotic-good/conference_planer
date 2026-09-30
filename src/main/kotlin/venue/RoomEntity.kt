package org.example.venue

import jakarta.persistence.CollectionTable
import jakarta.persistence.Column
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import org.example.program.ProgramSessionEntity
import java.util.UUID

@Entity
@Table(name = "rooms")
class RoomEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    var id: UUID? = null,

    @Column(name = "name", nullable = false, unique = true)
    var name: String,

    @Column(name = "capacity", nullable = false)
    var capacity: Int? = null,

    // 'mappedBy' points to the 'session' field in ProgramSessionEntity (does NOT create a new FK column)
    @OneToMany(mappedBy = "program_sessions", fetch = FetchType.LAZY)
    val program_sessions: MutableList<ProgramSessionEntity> = mutableListOf(),

    @Column(name = "location_notes", nullable = true)
    var location_notes: String? = null,

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
        name = "room_equipment",
        joinColumns = [JoinColumn(name = "room_id")]
    )
    @Column(name = "equipment")
    @Enumerated(EnumType.STRING)
    val equipment: MutableSet<EquipmentType> = mutableSetOf(),

    @Column(name = "available_from", insertable = false, updatable = false, nullable = false)
    var available_from: java.time.Instant,

    @Column(name = "available_to", insertable = false, updatable = false, nullable = false)
    var available_to: java.time.Instant,
    )

enum class EquipmentType {
    PROJECTOR,
    MICROPHONE,
    LIVESTREAMING,
    WORKSHOP_TABLES,
    WHEELCHAIR_ACCESS,
}