package org.example.country

import org.hamcrest.Matchers.hasSize
import org.mockito.Mockito
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import kotlin.test.Test

class CountryControllerTest {

    @Test
    fun `GET country returns countries as JSON`() {
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
        val controller = CountryController(CountryService(repository))
        val mockMvc = MockMvcBuilders.standaloneSetup(controller).build()

        mockMvc.get("/country")
            .andExpect {
                status { isOk() }
                content { contentType("application/json") }
                jsonPath("$", hasSize<Any>(1))
                jsonPath("$[0].countryCode") { value("CZ") }
                jsonPath("$[0].name") { value("Czechia") }
                jsonPath("$[0].population") { value(10_527_781) }
                jsonPath("$[0].euMember") { value(true) }
            }
    }
}
