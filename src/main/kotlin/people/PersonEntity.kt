package org.example.people

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import java.util.UUID

@Entity
@Table(name = "people")
class PersonEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    var id: UUID? = null,

    @field:NotBlank // application-level validation: Jakarta Bean Validation via @field:Email
    @field:Email(message = "Invalid email format")     // Checks pattern syntax (user@domain.com) in memory before hitting SQL
    @Column(name = "email", nullable = false, unique = true, length = 255) // database-level schema mapping
    var email: String,

    @Column(nullable = false)
    var display_name: String,

    @Column(nullable = false)
    var phone: String?= null,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var authentication_status: AuthStatus,

    @Column(name= "event_participation", nullable = false)
    var event_participation: MutableList<EventParticipantEntity> = mutableListOf(),

    @Column(nullable = false, insertable = false, updatable = false)
    var created_at: java.time.Instant
)

enum class AuthStatus {
    INVITED,
    ACTIVE,
    DISABLED,
}
