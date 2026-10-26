package org.example.event.api

import org.example.event.domain.CountryTimeZonesNotFoundException
import org.example.event.timezone.TimeZoneCatalog
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class TimeZoneController(
    private val timeZoneCatalog: TimeZoneCatalog,
) {
    @GetMapping("/countries/{countryCode}/time-zones")
    fun getTimeZones(@PathVariable countryCode: String): List<String> =
        timeZoneCatalog.findTimeZoneIds(countryCode).ifEmpty {
            throw CountryTimeZonesNotFoundException(countryCode)
        }
}
