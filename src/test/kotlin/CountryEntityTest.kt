package org.example.country

import kotlin.test.Test
import kotlin.test.assertEquals

class CountryEntityTest {

    @Test
    fun `maps persistence fields to the domain model`() {
        val entity = CountryEntity(
            countryCode = "CZ",
            name = "Czechia",
            population = 10_527_781,
            euMember = true,
        )

        val country = entity.toDomain()

        assertEquals("CZ", country.countryCode)
        assertEquals("Czechia", country.name)
        assertEquals(10_527_781, country.population)
        assertEquals(true, country.euMember)
    }
}
