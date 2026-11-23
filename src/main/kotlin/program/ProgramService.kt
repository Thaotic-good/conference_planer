package org.example.program

import org.example.event.EventEntity
import org.example.venue.EquipmentType
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.UUID

@Service
class ProgramService(
    programSessionRepository: ProgramSessionRepository,
) {
    // get list
    // get detail
    // create program session
    // update program session
    // delete program session
    // findByEvent
    // findBySessionParticipant
    // findByRoom
}

data class ProgramSessionDomain(
    val id: UUID,
    val requiredEquipment: MutableMap<EquipmentType, Int>,
    val sessionFormat: SessionFormat,
    val title: String,
    val description: String?,
    val minimalRequiredCapacity: Int,
    val event: EventEntity,
    val startsAt: Instant,
    val endsAt: Instant,
    val setupMinutes: Int?,
    val teardownMinutes: Int?,
    val status: ProgramSessionStatus,
    val createdAt: Instant,
    val updatedAt: Instant,
)