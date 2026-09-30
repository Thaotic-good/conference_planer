plugins {
    kotlin("jvm") version "2.4.10"
    kotlin("plugin.spring") version "2.4.10"

    id("org.springframework.boot") version "4.1.1"
    id("io.spring.dependency-management") version "1.1.7"
    kotlin("plugin.jpa") version "2.4.10" // compiler plugin generates non-argument constructors for @Entity classes under the hood (Hibernate require an internal no-arg constructor to instantiate entities via reflection)
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-webmvc")
    implementation(kotlin("reflect"))
    implementation("tools.jackson.module:jackson-module-kotlin")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation(kotlin("test"))
    testImplementation("org.springframework.boot:spring-boot-starter-data-jpa-test")

    implementation("org.springframework.boot:spring-boot-starter-flyway") //Flyway starter/driver?
    runtimeOnly("org.flywaydb:flyway-database-postgresql")
    runtimeOnly("org.postgresql:postgresql") //PostgreSQL driver?
}

kotlin {
    jvmToolchain(26)
}

allOpen { // In Kotlin, classes are final by default. JPA requires entity classes and lazy-loaded fields to be extensible for proxying
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

tasks.test {
    useJUnitPlatform()
}
