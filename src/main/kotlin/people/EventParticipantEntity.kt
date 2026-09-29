package org.example.people

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table
class EventPersonEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false)
    var id: String? = null,

    @Column(nullable = false)
    var event_id: String? = null,

    @Column(nullable = false)
    var person_id: String? = null,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var participation_status: ParticipationStatus? = null,

    @Column(nullable = false)
    var maximum_hours_per_day: Int? = null,

    @Column(nullable = false)
    var emergency_contact: String? = null,

    @Column(nullable = false)
    var notes: String? = null,
)

enum class ParticipationStatus {
    INVITED,
    CONFIRMED,
    DECLINED,
    INACTIVE
}