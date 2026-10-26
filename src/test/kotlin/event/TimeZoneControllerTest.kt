package org.example.event

import org.example.event.api.EventExceptionHandler
import org.example.event.api.TimeZoneController
import org.example.event.timezone.TimeZoneCatalog
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.time.ZoneId
import kotlin.test.Test

class TimeZoneControllerTest {
    @Test
    fun `returns time-zone IDs for a country`() {
        val mockMvc = MockMvcBuilders
            .standaloneSetup(TimeZoneController(ControllerTimeZoneCatalog()))
            .setControllerAdvice(EventExceptionHandler())
            .build()

        mockMvc.get("/countries/CZ/time-zones")
            .andExpect {
                status { isOk() }
                content { json("[\"Europe/Prague\"]") }
            }
    }

    @Test
    fun `returns not found when a country has no supported zones`() {
        val mockMvc = MockMvcBuilders
            .standaloneSetup(TimeZoneController(ControllerTimeZoneCatalog()))
            .setControllerAdvice(EventExceptionHandler())
            .build()

        mockMvc.get("/countries/XX/time-zones")
            .andExpect {
                status { isNotFound() }
                jsonPath("$.status") { value(404) }
            }
    }
}

private class ControllerTimeZoneCatalog : TimeZoneCatalog {
    override fun findTimeZoneIds(countryCode: String): List<String> =
        if (countryCode.equals("CZ", ignoreCase = true)) listOf("Europe/Prague") else emptyList()

    override fun supports(countryCode: String, zoneId: ZoneId): Boolean =
        zoneId.id in findTimeZoneIds(countryCode)
}
