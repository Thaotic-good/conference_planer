package org.example.country

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.validation.constraints.Min
import org.springframework.data.jpa.repository.JpaRepository

@Entity
@Table(name = "country")
class CountryEntity(
    @Id
    @Column(name = "country_code", nullable = false, length = 2)
    var countryCode: String,

    @Column(unique = true, nullable = false)
    var name: String,

    @get:Min(0)
    @Column(nullable = false)
    var population: Int,

    @Column(name = "eu_member", nullable = false)
    var euMember: Boolean,
) {
    fun toDomain(): CountryDomain {
        return CountryDomain(
            countryCode = countryCode,
            name = name,
            population = population,
            euMember = euMember,
        )
    }
}

interface CountryRepository : JpaRepository<CountryEntity, String>
