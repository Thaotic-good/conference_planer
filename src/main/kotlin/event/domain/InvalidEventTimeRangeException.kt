package org.example.event.domain

class InvalidEventTimeRangeException(message: String = "Event start time must be before end time") : RuntimeException(message)

class InvalidCountryTimeZoneException(countryCode: String, timeZoneId: String) : RuntimeException(
    "Time zone '$timeZoneId' is not available for country '$countryCode'.",
)

class InvalidEventLocalDateTimeException(message: String) : RuntimeException(message)

class CountryTimeZonesNotFoundException(countryCode: String) : RuntimeException(
    "No supported time zones were found for country '$countryCode'.",
)
