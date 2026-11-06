package org.example.event.api

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.example.event.domain.CountryTimeZonesNotFoundException
import org.example.event.timezone.TimeZoneCatalog
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Catalogs", description = "Reference data used by event forms")
@RestController
class TimeZoneController(
    private val timeZoneCatalog: TimeZoneCatalog,
) {
    @Operation(
        summary = "List supported time zones for a country",
        description = "Returns sorted, canonical, Java-compatible IANA time-zone IDs for an ISO alpha-2 country code.",
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Supported time zones returned"),
            ApiResponse(
                responseCode = "404",
                description = "The country code has no supported time zones",
                content = [Content(schema = Schema(implementation = ApiErrorResponse::class))],
            ),
        ],
    )
    @GetMapping("/countries/{countryCode}/time-zones")
    fun getTimeZones(
        @Parameter(description = "ISO 3166 alpha-2 country code", example = "CZ")
        @PathVariable countryCode: String,
    ): List<String> =
        timeZoneCatalog.findTimeZoneIds(countryCode).ifEmpty {
            throw CountryTimeZonesNotFoundException(countryCode)
        }
}
