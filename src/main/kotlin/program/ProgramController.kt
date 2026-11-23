package org.example.program

import org.example.event.EventEntity
import org.example.venue.EquipmentType
import java.time.Instant

data class ProgramSessionSummaryDto(
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

data class ProgramSessionDetailDto(
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