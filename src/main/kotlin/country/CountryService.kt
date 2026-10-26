package org.example.country

import org.springframework.stereotype.Service

@Service
class CountryService(
    val countryRepository: CountryRepository,
) {
    fun findAll(): List<CountryDomain> = countryRepository.findAll().map { it.toDomain() }
}

data class CountryDomain(
    val countryCode: String,
    val name: String,
    val population: Int,
    val euMember: Boolean,
) {
    fun toDto(): CountryDto {
        return CountryDto(
            countryCode = countryCode,
            name = name,
            population = population,
            euMember = euMember,
        )
    }
}
