package org.example.country

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class CountryController(
    val countryService: CountryService,
) {
    @GetMapping("/country")
    fun getAllCountries(): List<CountryDto> = countryService.findAll().map { it.toDto() }
}

data class CountryDto(
    val countryCode: String,
    val name: String,
    val population: Int,
    val euMember: Boolean,
)
