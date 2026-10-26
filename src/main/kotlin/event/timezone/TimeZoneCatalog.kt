package org.example.event.timezone

import com.ibm.icu.util.TimeZone as IcuTimeZone
import org.springframework.stereotype.Component
import java.time.ZoneId
import java.util.Locale
import java.util.concurrent.ConcurrentHashMap

interface TimeZoneCatalog {
    fun findTimeZoneIds(countryCode: String): List<String>
    fun supports(countryCode: String, zoneId: ZoneId): Boolean
}

@Component
class IcuTimeZoneCatalog : TimeZoneCatalog {
    private val timeZoneIdsByCountry = ConcurrentHashMap<String, List<String>>()
    private val javaTimeZoneIds = ZoneId.getAvailableZoneIds()

    override fun findTimeZoneIds(countryCode: String): List<String> {
        val normalizedCountryCode = countryCode.trim().uppercase(Locale.ROOT)
        if (!normalizedCountryCode.matches(ISO_COUNTRY_CODE)) {
            return emptyList()
        }

        return timeZoneIdsByCountry.computeIfAbsent(normalizedCountryCode) { code ->
            IcuTimeZone.getAvailableIDs(
                IcuTimeZone.SystemTimeZoneType.CANONICAL_LOCATION,
                code,
                null,
            )
                .asSequence()
                .map(IcuTimeZone::getIanaID)
                .filter { it != IcuTimeZone.UNKNOWN_ZONE_ID }
                .filter { it in javaTimeZoneIds }
                .distinct()
                .sorted()
                .toList()
        }
    }

    override fun supports(countryCode: String, zoneId: ZoneId): Boolean =
        zoneId.id in findTimeZoneIds(countryCode)

    private companion object {
        val ISO_COUNTRY_CODE = Regex("[A-Z]{2}")
    }
}
