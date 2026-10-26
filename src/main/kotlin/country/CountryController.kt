package org.example.country

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class CountryController(
    private val countryService: CountryService,
) {
    @GetMapping("/countries")
    fun getAllCountries(): List<CountryDto> = countryService.findAll().map(Country::toDto)
}

data class CountryDto(
    val countryCode: String,
    val name: String,
)

private fun Country.toDto(): CountryDto = CountryDto(
    countryCode = countryCode,
    name = name,
)
