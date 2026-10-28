# Conference Planner Project Context

## Purpose

This repository is both a conference-planning backend and a practical way to
learn Kotlin and Spring Boot. The application should eventually coordinate
events, people, program sessions, rooms, teams, shifts, and tasks.

The learning goal is to understand why each application layer exists and how
data moves through the system, rather than to scaffold every layer at once.

## Technology

- Kotlin with Gradle Kotlin DSL
- Spring Boot, Spring MVC, Spring Data JPA, and Bean Validation
- PostgreSQL
- Flyway versioned database migrations
- JUnit through the Spring Boot test starter

## Request and data flow

```text
HTTP request
    -> controller + request/response DTOs
    -> service + business rules
    -> repository
    -> JPA entity mapping
    -> PostgreSQL schema managed by Flyway
```

Not every feature needs a separate domain object immediately. A domain type is
valuable when it expresses or protects business behavior. A DTO is valuable at
the HTTP boundary because the API contract should not depend directly on JPA.

## Development strategy

Build vertical slices, one observable use case at a time:

1. State the user-visible use case and expected behavior.
2. Add or update the Flyway migration for the required schema.
3. Map the schema with JPA entities.
4. Add a repository and only the queries required by the use case.
5. Implement service behavior and business rules.
6. Define request and response DTOs.
7. Expose the behavior through a controller.
8. Test the persistence, service, and HTTP boundaries as appropriate.
9. Restore a green build before starting the next slice.

## Current state

The event creation slice stores a country code and time-zone ID and validates
their relationship against ICU4J before converting local date-times to
instants. ICU4J is also the runtime source for the country list: the country
service exposes only ISO country codes that have at least one supported time
zone. Countries are reference data, not persisted application entities.

`GET /countries` returns English country names and alpha-2 codes for the event
form. `GET /countries/{countryCode}/time-zones` supplies the next set of form
choices. The country catalog, service filtering, HTTP response, time-zone
catalog, event service, and event persistence each have focused tests.

`POST /events` creates a draft event. The service rejects unsupported
country/time-zone pairs, nonexistent or ambiguous local times at daylight-saving
transitions, and end times that are not after start times. The V1 migration
creates the event table and its constraints.

`GET /events` currently returns event names. `GET /events/{id}` returns an
`EventDto` without an ID, while creation returns an `EventResponse` including
an ID. `EventDomain.toDto()` imports the API DTO; another response mapping lives
in the controller. Event service, entity, and repository tests exist; a dedicated
event controller test is not currently present.

Early entities and repository definitions also exist for events, people,
program sessions, rooms, staffing, and tasks. Several corresponding controller
and service files are placeholders. Treat these as work in progress, not as an
architecture that must be copied unchanged.

Earlier project notes report a passing `./gradlew test` run; the suite was not
rerun during the documentation reconciliation on 2026-10-28. The early non-event entities have not yet
been verified as one application-wide JPA model; keep each new vertical slice
narrow and address its mappings when its use case requires them.

## Learning workflow

The active exercise and its next action live in [LEARNING_LOG.md](LEARNING_LOG.md).
The proposed progression and teaching workflow live in
[LEARNING_ROADMAP.md](LEARNING_ROADMAP.md). This file records application facts,
not learner understanding or current exercise progress.

## Architectural decisions

- Flyway, rather than Hibernate schema generation, owns schema evolution.
- Database constraints enforce data integrity; matching validation and JPA
  annotations document and reject invalid input earlier.
- API DTOs and persistence entities are separate contracts.
- Repository methods are driven by use cases, not by a desire to cover every
  entity property.
- ISO country codes, English country names, and country/time-zone associations
  come from the ICU4J data bundled with the application rather than database
  seed data.
- Package code by conference capability where practical, while keeping each
  capability's layers easy to locate.
