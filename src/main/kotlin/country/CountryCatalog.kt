package org.example.country

import com.ibm.icu.util.ULocale
import org.springframework.stereotype.Component

data class Country(
    val countryCode: String,
    val name: String,
)

interface CountryCatalog {
    fun findAll(): List<Country>
}

@Component
class IcuCountryCatalog : CountryCatalog {
    override fun findAll(): List<Country> = ULocale.getISOCountries()
        .asSequence()
        .distinct()
        .map { countryCode ->
            val countryLocale = ULocale.Builder()
                .setRegion(countryCode)
                .build()

            Country(
                countryCode = countryCode,
                name = countryLocale.getDisplayCountry(ULocale.ENGLISH),
            )
        }
        .toList()
}
