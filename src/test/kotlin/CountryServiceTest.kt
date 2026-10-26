package org.example.country

import org.mockito.Mockito
import kotlin.test.Test
import kotlin.test.assertEquals

class CountryServiceTest {

    @Test
    fun `findAll returns countries supplied by the repository`() {
        val repository = Mockito.mock(CountryRepository::class.java)
        Mockito.`when`(repository.findAll()).thenReturn(
            listOf(
                CountryEntity(
                    countryCode = "CZ",
                    name = "Czechia",
                    population = 10_527_781,
                    euMember = true,
                ),
            ),
        )
        val service = CountryService(repository)

        val countries = service.findAll()

        assertEquals(
            listOf(
                CountryDomain(
                    countryCode = "CZ",
                    name = "Czechia",
                    population = 10_527_781,
                    euMember = true,
                ),
            ),
            countries,
        )
    }
}
