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

## Learning continuity and documentation

- `docs/PROJECT_CONTEXT.md` owns lasting application facts, architecture, and
  limitations. Update it only when those facts change; verify against code.
- `docs/LEARNING_ROADMAP.md` owns the proposed progression and teaching workflow.
  Read it when choosing or changing a learning topic.
- `docs/LEARNING_LOG.md` records learning conversations, not agent activity.
  Keep one active exercise: topic, short goal, and one pending question. Add a
  brief misconception only when revealed by the learner's answer.
- Do not log every milestone or session. Update the log only when the learning
  topic changes, the learner supplies a reflection, or discussion reveals or
  resolves a misconception. Session-end updates are optional.
- Keep implementation status, test results, validation checklists, operational
  next steps, and agent work summaries out of the learning log. Report relevant
  implementation and validation results in the ordinary task handoff.
- During a learning exercise, explain the concept and ask one concrete question
  in chat, using a short code snippet, a failure scenario, or the produced code.
  Writing a pending question in a file does not count as asking the learner.
  Wait for their answer before assessing it; do not supply the answer alongside
  the question. Follow with another concept only after discussing the first.
- An implementation request still authorizes implementation. When it introduces
  the active learning concept, include a focused understanding question in the
  handoff. Routine fixes and documentation edits need no artificial quiz.
- First-person learning claims must come from the learner. Codex may transcribe
  conversational reflections without strengthening them. Do not create empty
  reflection records or "understanding unassessed" entries for agent work.
- Add dated learning records only after learner participation supplies something
  meaningful to record. Keep any Codex assessment separate and brief, grounded
  in the learner's reasoning. Existing code and passing tests do not prove
  understanding.
- Correct a misconception by naming the mistaken assumption, explaining it with
  code or a counterexample, and asking the learner to apply the correction to a
  small variation. Do not mark the core concept understood until demonstrated;
  repeating Codex's wording alone is insufficient.
- Respect a pause or change of focus. Preserve a useful pending learning question
  without forcing continued questioning or blocking authorized implementation.

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
