package org.example.country

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class IcuCountryCatalogTest {
    private val catalog = IcuCountryCatalog()

    @Test
    fun `returns unique ISO country codes with English names`() {
        val countries = catalog.findAll()
        val czechia = countries.single { it.countryCode == "CZ" }

        assertEquals("Czechia", czechia.name)
        assertEquals(countries.size, countries.map(Country::countryCode).distinct().size)
        assertTrue(countries.all { it.countryCode.matches(Regex("[A-Z]{2}")) })
        assertTrue(countries.all { it.name.isNotBlank() })
    }
}
