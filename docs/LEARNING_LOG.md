# Learning Log

Use this file to preserve learning continuity between sessions. Keep entries
short, factual, and dated when recording a completed lesson. Update the current
exercise instead of maintaining a detailed implementation changelog.

## Mission

Learn Kotlin and Spring Boot by incrementally building a conference-planning
backend and understanding the responsibility of every layer that is added.

## Concepts already encountered

- Kotlin primary constructors, data classes, nullability, and collection types
- Spring dependency injection with constructor parameters
- REST controllers and `@GetMapping`
- JPA entities, identifiers, columns, relationships, and repositories
- Mapping persistence entities to domain or response types
- PostgreSQL configuration
- Flyway versioned migrations and seed data

Encountering a concept does not yet mean it has been mastered.

## Questions to reinforce

- When should a repository query be added instead of filtering in memory?
- Which rules belong in the database, Bean Validation, the service, or more
  than one of those places?
- When does a separate domain model add value over entity-to-DTO mapping?
- How should JPA relationships be modeled from both the Kotlin and database
  sides?
- How should repository, service, and controller tests differ?

## Active exercise

Updated: 2026-10-28 (documentation reconciliation).

- Exercise: Consolidate event responses, beginning with a responsibility review.
- Primary concept: Responsibilities and dependency direction at the HTTP boundary.
- Implementation: planned. Current code has separate creation and retrieval
  response types; retrieval omits the ID. `EventDomain.toDto()` depends on an
  API DTO, while another mapping lives in the controller.
- Understanding: not assessed. No learner explanation of this decision has
  been recorded.
- Learner reflection pending.
- Unresolved question: Which classes should change when the JSON response
  contract changes, and why?
- Exact next action: Ask the learner to trace the create and get-by-ID response
  mappings and propose where response conversion should live. Agree on the
  intended response contract and acceptance criteria before implementation.
- Validation to plan with the exercise: focused HTTP response checks and
  `./gradlew test` after code changes. No tests were run for this documentation
  update.

This is the only place tracking the active exercise. The proposed later
milestones live in [LEARNING_ROADMAP.md](LEARNING_ROADMAP.md).

## Learner reflection and assessment format

First-person claims must come from the learner, including reflections supplied
conversationally and transcribed by Codex. Codex may clarify wording without
strengthening the claim. If no reflection was supplied, record "Learner
reflection pending" instead of filling in answers.

```text
YYYY-MM-DD — Topic
What I can now explain or do:
Evidence (exercise, test, or example):
What remains unclear:

Codex assessment:
Understanding: not assessed | developing | demonstrated
Basis: the learner's explanation or application to a variation.
Correction or follow-up: any remaining conceptual gap or next step.
```

Use the date of the learner's reflection. Attribute implementation observations
and validation separately; passing tests do not establish understanding. When
an explanation is mistaken, correct the assumption and reassess through a small
variation before marking the core concept demonstrated. Leave incomplete
reflections and unresolved assessments explicitly pending.

## Completed learning records

No learner-confirmed reflections have been established during this
reconciliation. Add records here as the learner supplies explanations and they
are assessed. Historical implementation notes below are not completed learning
records.

## Historical implementation notes

Reclassified on 2026-10-28: the previous entries used first-person learning
claims, but their learner authorship and understanding were not established in
this session. Their factual history is retained below without asserting mastery.

### 2026-09-30 — Country reference slice and focused tests

- Historical note: a persisted country reference slice had entity, repository,
  service, and controller tests. The earlier entry reported a passing
  `./gradlew test` run using Testcontainers PostgreSQL and Flyway.
- Those country persistence types and tests describe an earlier implementation,
  not the current runtime catalog design.
- Learner reflection pending.
- Codex assessment: understanding not assessed; no supporting learner
  explanation established during reconciliation.
- Question carried forward from the old note, not attributed to the learner:
  When does a separate domain type add value over a field-only mapping?

### 2026-10-26 — Runtime country and time-zone reference catalogs

- Historical note: ICU-backed runtime catalogs replaced country persistence.
  Current code includes `CountryCatalog`, `TimeZoneCatalog`, and country
  service filtering by supported time zones, with focused tests.
- Learner reflection pending.
- Codex assessment: understanding not assessed; implementation alone does not
  establish the learner's reasoning.
- Question carried forward from the old note, not attributed to the learner:
  When should country names follow the request locale rather than fixed English?

### 2026-10-28 — Event slice documentation reconciliation

- Code inspection confirms event creation, local-time validation and conversion,
  event persistence, and the V1 migration already exist. The former exercise to
  define initial creation behavior and the former next lesson to create the
  table were stale and have been replaced.
- Event entity, service, and repository tests exist. A dedicated event
  controller test is not currently present. This documentation update did not
  rerun tests or establish that every event HTTP behavior is verified.
- Learner reflection pending. The learner reported learning a lot from the
  event slice; specific conceptual understanding has not yet been assessed.
