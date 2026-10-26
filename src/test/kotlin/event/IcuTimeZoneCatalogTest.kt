package org.example.event

import org.example.event.timezone.IcuTimeZoneCatalog
import org.junit.jupiter.api.Test
import java.time.ZoneId
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class IcuTimeZoneCatalogTest {
    private val catalog = IcuTimeZoneCatalog()

    @Test
    fun `finds Java-compatible IANA time zones for a country`() {
        val timeZoneIds = catalog.findTimeZoneIds("cz")

        assertEquals(listOf("Europe/Prague"), timeZoneIds)
        assertEquals(timeZoneIds.sorted(), timeZoneIds)
        assertEquals(timeZoneIds.distinct(), timeZoneIds)
        timeZoneIds.forEach(ZoneId::of)
    }

    @Test
    fun `reports whether a time zone belongs to a country`() {
        assertTrue(catalog.supports("CZ", ZoneId.of("Europe/Prague")))
        assertFalse(catalog.supports("CZ", ZoneId.of("America/New_York")))
    }

    @Test
    fun `returns no options for an invalid country code`() {
        assertTrue(catalog.findTimeZoneIds("invalid").isEmpty())
    }
}
