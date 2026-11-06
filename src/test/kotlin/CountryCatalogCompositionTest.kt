package org.example.country

import org.example.event.timezone.IcuTimeZoneCatalog
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CountryCatalogCompositionTest {
    // real adapters, no mocks
    private val timeZoneCatalog = IcuTimeZoneCatalog()
    private val icuCountryCatalog = IcuCountryCatalog()

    private val countryService = CountryService(
        countryCatalog = icuCountryCatalog,
        timeZoneCatalog = timeZoneCatalog,
    )

    @Test
    fun `real catalogs produce sorted countries with supported time zones`() {
        val countries = countryService.findAll()

        assertTrue(countries.any { it == Country(countryCode = "CZ", name = "Czechia") })
        assertTrue(
            countries.all { country ->
                timeZoneCatalog.findTimeZoneIds(country.countryCode).isNotEmpty()
            },
        )
        assertEquals(
            countries.sortedWith(compareBy(Country::name, Country::countryCode)),
            countries,
        )
    }
}
