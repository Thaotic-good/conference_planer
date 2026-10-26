package org.example.country

import org.hamcrest.Matchers.hasSize
import org.example.event.timezone.TimeZoneCatalog
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.time.ZoneId
import kotlin.test.Test

class CountryControllerTest {

    @Test
    fun `GET countries returns supported countries as JSON`() {
        val controller = CountryController(
            CountryService(
                countryCatalog = ControllerCountryCatalog(),
                timeZoneCatalog = ControllerTimeZoneCatalog(),
            ),
        )
        val mockMvc = MockMvcBuilders.standaloneSetup(controller).build()

        mockMvc.get("/countries")
            .andExpect {
                status { isOk() }
                content { contentType("application/json") }
                jsonPath("$", hasSize<Any>(1))
                jsonPath("$[0].countryCode") { value("CZ") }
                jsonPath("$[0].name") { value("Czechia") }
            }
    }
}

private class ControllerCountryCatalog : CountryCatalog {
    override fun findAll(): List<Country> = listOf(Country("CZ", "Czechia"))
}

private class ControllerTimeZoneCatalog : TimeZoneCatalog {
    override fun findTimeZoneIds(countryCode: String): List<String> =
        if (countryCode == "CZ") listOf("Europe/Prague") else emptyList()

    override fun supports(countryCode: String, zoneId: ZoneId): Boolean =
        zoneId.id in findTimeZoneIds(countryCode)
}
