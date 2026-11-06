package org.example.country

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Catalogs", description = "Reference data used by event forms")
@RestController
class CountryController(
    private val countryService: CountryService,
) {
    @Operation(
        summary = "List countries available for event creation",
        description = "Returns English country names and ISO alpha-2 codes, sorted by name. " +
            "Every returned country has at least one supported time zone.",
    )
    @ApiResponse(responseCode = "200", description = "Supported countries returned")
    @GetMapping("/countries")
    fun getAllCountries(): List<CountryDto> = countryService.findAll().map(Country::toDto)
}

data class CountryDto(
    @field:Schema(description = "ISO 3166 alpha-2 country code", example = "CZ")
    val countryCode: String,

    @field:Schema(description = "English country name", example = "Czechia")
    val name: String,
)

private fun Country.toDto(): CountryDto = CountryDto(
    countryCode = countryCode,
    name = name,
)
