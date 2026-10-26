package org.example.country

import org.example.event.timezone.TimeZoneCatalog
import java.time.ZoneId
import kotlin.test.Test
import kotlin.test.assertEquals

class CountryServiceTest {

    @Test
    fun `findAll returns only countries with supported time zones sorted by name`() {
        val service = CountryService(
            countryCatalog = StubCountryCatalog(
                listOf(
                    Country(countryCode = "DE", name = "Germany"),
                    Country(countryCode = "BV", name = "Bouvet Island"),
                    Country(countryCode = "CZ", name = "Czechia"),
                ),
            ),
            timeZoneCatalog = StubTimeZoneCatalog(
                mapOf(
                    "CZ" to listOf("Europe/Prague"),
                    "DE" to listOf("Europe/Berlin"),
                ),
            ),
        )

        val countries = service.findAll()

        assertEquals(
            listOf(
                Country(countryCode = "CZ", name = "Czechia"),
                Country(countryCode = "DE", name = "Germany"),
            ),
            countries,
        )
    }
}

private class StubCountryCatalog(
    private val countries: List<Country>,
) : CountryCatalog {
    override fun findAll(): List<Country> = countries
}

private class StubTimeZoneCatalog(
    private val timeZoneIdsByCountry: Map<String, List<String>>,
) : TimeZoneCatalog {
    override fun findTimeZoneIds(countryCode: String): List<String> =
        timeZoneIdsByCountry[countryCode].orEmpty()

    override fun supports(countryCode: String, zoneId: ZoneId): Boolean =
        zoneId.id in findTimeZoneIds(countryCode)
}
