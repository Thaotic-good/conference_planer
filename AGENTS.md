# Conference Planner Codex Guide

## Project mission

- This is a learning project for building a conference planner with Kotlin,
  Spring Boot, Spring MVC, Spring Data JPA, PostgreSQL, and Flyway.
- Optimize for understanding and maintainable code, not for implementing the
  largest possible amount of functionality in one change.
- Before substantial work, read `docs/PROJECT_CONTEXT.md` and
  `docs/LEARNING_LOG.md`. Inspect the code for implementation details instead
  of assuming those documents are perfectly current.

## Guided-learning behavior

- By default, explain the relevant concept, break the work into a small
  exercise, and let the learner attempt it before supplying a complete
  solution.
- Review the learner's code by identifying what works, what is incorrect, and
  why. Prefer focused corrections over silently replacing an entire feature.
- Ask short questions when they help reveal understanding, but do not turn
  every task into a quiz.
- An explicit request to implement, fix, build, or update authorizes Codex to
  make the requested changes. Explain the important lessons in the handoff.
- Keep each learning milestone compiling before starting another feature.

## Architecture conventions

- Develop one end-to-end use case at a time rather than creating every layer
  for every entity up front.
- Controllers own HTTP concerns and convert request/response DTOs. Do not
  expose JPA entities as the public API contract.
- Services coordinate use cases, transactions, and business rules.
- Repositories own persistence access. Add derived queries or explicit queries
  only when a use case requires them; do not generate finders for every field.
- JPA entities describe persistence mappings. Introduce separate domain types
  when they protect meaningful business rules, not merely to satisfy a layer
  diagram.
- Flyway owns the database schema. Add a versioned migration for every schema
  change, and keep constraints in the database as the final source of truth.
- Prefer idiomatic Kotlin names in code (`camelCase`) and map them explicitly
  to `snake_case` database columns when needed.

## Validation

- Run `./gradlew test` after Kotlin, configuration, or migration changes.
- If the project does not compile, report the first actionable compiler errors
  and restore compilation before expanding the feature.
- Add focused repository, service, and HTTP tests for each completed vertical
  slice.

## Planning versus implementation

- In Plan mode, do not modify files or run commands that change project state.
  Investigate the repository and present:
  1. the proposed solution,
  2. the files that would change,
  3. important tradeoffs, and
  4. the validation steps.
  Wait for explicit approval before implementation.

- In Default mode, requests phrased as "implement", "fix", "build", or
  "update" authorize implementation. Inspect the code, make the changes, run
  relevant checks, and report the result.

- If the user says "preview", "propose", "design", or "show me first", do not
  modify files until the user explicitly approves implementation.
