package org.example

import jakarta.persistence.*
import jakarta.validation.constraints.Min
import org.springframework.data.jpa.repository.JpaRepository

@Entity
@Table(name = "country") //connects the entity to the table named country
class CountryEntity ( // primary constructor
    @Id
    @Column(name = "country_code", nullable = false, length = 2)
    var countryCode: String,

    @Column(unique = true, nullable = false)
    var name: String,

    @get:Min(0)
    @Column(nullable = false)
    var population: Int,

    @Column(name = "eu_member", nullable = false) // maps "euMember" to the SQL column "eu_member"
    var euMember:  Boolean // idiomatic Kotlin naming
) {
   fun toDomain(): CountryDomain {
       return CountryDomain(
           countryCode = this.countryCode,
           name = this.name,
           population = this.population,
           euMember = this.euMember,
       )
   }
}

interface CountryRepository : JpaRepository<CountryEntity, String>