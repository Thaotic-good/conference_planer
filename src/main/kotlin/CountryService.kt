package org.example

import org.springframework.stereotype.Service

@Service
class CountryService(
    val countryRepository: CountryRepository,// injecting a repository into a service
) {
    fun findAll(): List<CountryDomain> = countryRepository.findAll().map { it.toDomain() }
}

data class CountryDomain(
    val countryCode: String,
    val name: String,
    val population: Int,
    val euMember:  Boolean,
) {
    fun toDto() : CountryDto {
        return CountryDto(
            countryCode = this.countryCode,
            name = this.name,
            population = this.population,
            euMember = this.euMember,
        )
    }
}

