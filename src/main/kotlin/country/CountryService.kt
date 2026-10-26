package org.example.country

import org.example.event.timezone.TimeZoneCatalog
import org.springframework.stereotype.Service

@Service
class CountryService(
    private val countryCatalog: CountryCatalog,
    private val timeZoneCatalog: TimeZoneCatalog,
) {
    private val supportedCountries: List<Country> by lazy {
        countryCatalog.findAll()
            .filter { timeZoneCatalog.findTimeZoneIds(it.countryCode).isNotEmpty() }
            .sortedWith(compareBy(Country::name, Country::countryCode))
    }

    fun findAll(): List<Country> = supportedCountries
}
