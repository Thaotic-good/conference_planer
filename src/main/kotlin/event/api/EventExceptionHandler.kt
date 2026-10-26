package org.example.event.api

import org.example.event.domain.CountryTimeZonesNotFoundException
import org.example.event.domain.InvalidCountryTimeZoneException
import org.example.event.domain.InvalidEventLocalDateTimeException
import org.example.event.domain.InvalidEventTimeRangeException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.time.DateTimeException

// ensures that responses are returned as JSON
@RestControllerAdvice
class EventExceptionHandler {
    @ExceptionHandler(CountryTimeZonesNotFoundException::class)
    // Spring: controllers, event listeners, scheduled methods, and exception handlers appear unused because annotations connect them
    // (not ordinary Kotlin method calls)
    fun handleCountryTimeZonesNotFound(ex: CountryTimeZonesNotFoundException): ResponseEntity<ApiErrorResponse> =
        error(HttpStatus.NOT_FOUND, ex.message ?: "No supported time zones were found.")

    @ExceptionHandler(
        InvalidCountryTimeZoneException::class,
        InvalidEventLocalDateTimeException::class,
        InvalidEventTimeRangeException::class,
        DateTimeException::class,
        MethodArgumentNotValidException::class,
    )
    fun handleBadRequest(ex: Exception): ResponseEntity<ApiErrorResponse> =
        error(HttpStatus.BAD_REQUEST, ex.message ?: "The request is invalid.")

    private fun error(status: HttpStatus, message: String): ResponseEntity<ApiErrorResponse> =
        ResponseEntity.status(status).body(
            ApiErrorResponse(
                status = status.value(),
                error = status.reasonPhrase,
                message = message,
            ),
        )
}

data class ApiErrorResponse(
    val status: Int,
    val error: String,
    val message: String,
)
