package org.example.venue

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import org.example.event.EventEntity
import org.example.program.ProgramSessionEntity
import java.util.UUID

@Entity
@Table(name = "rooms")
class RoomEntity(
    @Id
    @GeneratedValue
    @Column(name = "room_id", updatable = false, nullable = false)
    var room_id: UUID? = null,

    // 'mappedBy' points to the 'session' field in ProgramSessionEntity (does NOT create a new FK column)
    @OneToMany(mappedBy = "program_sessions", fetch = FetchType.LAZY)
    val program_sessions: MutableList<ProgramSessionEntity> = mutableListOf(),

    @Column(name = "name", nullable = false, unique = true)
    var name: String,

    @Column(name = "capacity", nullable = false)
    var capacity: Int? = null,

    @Column(name = "location_notes", nullable = true)
    var location_notes: String? = null,

    @Column(name = "accessible", nullable = true)
    var accessible: Boolean? = null,

    @Column(name = "available", nullable = false)
    var available: String? = null,

    // maybe add room_capability:
    // projector
    //handheld_microphone
    //livestreaming
    //workshop_tables

    )