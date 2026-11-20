# Learning Roadmap

## Purpose

Build the conference planner through small, useful features that develop
understanding of Kotlin, object-oriented design, Spring Boot, and persistence.

This roadmap is a direction, not a fixed syllabus. Reorder milestones when
implementation problems reveal a more useful next lesson.

## Learning approach

Each milestone has:
- A user-visible outcome.
- One primary learning concept.
- Prerequisites to revisit when needed.
- A small implementation exercise.
- Evidence of understanding supplied by the learner.

Introduce abstractions and patterns when a concrete requirement justifies them.
Do not add layers or interfaces merely to practice their names.

## Proposed progression

| Milestone | Application outcome | Primary concept | Understanding checkpoint |
|---|---|---|---|
| Consolidate event responses | Consistent event API responses | Responsibilities and dependency direction | Explain where HTTP mapping belongs and what should change when the response contract changes |
| Confirm an event | Enforce agreed event status transitions | Encapsulation and invariants | Explain how the design prevents invalid transitions and why unrestricted mutation weakens that protection |
| Add a program session | Associate a persisted session with an event | Composition and persistence relationships | Explain the foreign key, relationship ownership, and transaction boundary |
| Validate session timing | Keep sessions within event boundaries | Cohesion and responsibility placement | Justify where each rule belongs and distinguish domain rules from input validation |
| Prevent room conflicts | Reject conflicting room bookings | Queries and consistency | Explain overlap detection and why checking before saving may fail with concurrent requests |
| Notify on confirmation | Support a concrete notification requirement, then a second implementation | Polymorphism and dependency inversion | Explain what varies, what stays stable, and whether an interface is justified |

Split each milestone into smaller exercises as needed. Revisit constructor
injection, nullability, object relationships, SQL constraints, and testing at the
point each is needed. Do not begin another feature while the current
implementation fails to compile.

## Session workflow

### 1. Choose the exercise

Read PROJECT_CONTEXT.md and the active exercise in LEARNING_LOG.md, then inspect
the relevant code. Agree on observable behavior, the primary concept, a small
learner task, and how the result will be checked.

If documentation and code disagree, correct factual documentation. Do not infer
learner understanding from existing code. Keep the active topic, short goal, and one pending
learning question only in LEARNING_LOG.md.

### 2. Let the learner attempt it

Explain enough to make the exercise approachable, then allow the learner to
reason or implement. Prefer a focused hint before a complete solution.

An explicit implementation request authorizes Codex to implement, but does not
establish learner understanding.

### 3. Ask and review in chat

Ask one concrete understanding question in chat, grounded in a short code
snippet, a failure scenario, or the produced code. Do not merely write the
question into the learning log. Do not give its answer before the learner has
attempted it. When an implementation introduces the active concept, include the
question in the handoff; routine fixes and documentation edits need no quiz.

Wait for the learner's answer. Identify what is correct, what is mistaken, and
why. Separate conceptual misunderstandings from syntax and framework details.
Discuss one concept before moving to another.

### 4. Resolve misunderstandings

When an explanation reveals a misconception:
1. Identify the specific mistaken assumption.
2. Explain it using current code or a minimal counterexample.
3. Ask the learner to apply the corrected idea to a small variation.
4. Reassess the explanation.

The core concept remains unresolved until the learner demonstrates it. Repeating
Codex's wording alone is insufficient. Avoid quizzing on incidental details.
If the learner pauses or changes focus, preserve a useful pending question
without forcing continued questioning.

### 5. Record learning only when useful

The learning log records learning conversations, not implementation progress.
Update it only when the topic changes, the learner supplies a reflection, or
discussion reveals or resolves a misconception. Do not record every milestone.
End-of-session updates are optional.

Keep the active exercise brief: topic, short goal, and one pending question.
If needed, add one sentence identifying a misconception shown by the learner's
answer. Exclude implementation status, test results, validation checklists,
operational next steps, and agent work summaries.

Add a dated record only after learner participation provides meaningful content.
The learner's conversational explanation can supply the reflection; do not
require a form after every exchange. Preserve their meaning and do not invent
first-person claims. Do not create empty reflection or unassessed records.
A separate Codex assessment is optional and should be brief, adding a useful
correction or evidence rather than repeating the reflection.

## Completion criteria

Verify application behavior with appropriate checks and report the results in
the task handoff. Passing tests establish neither learning nor understanding.

Consider a learning exercise complete when the learner explains or applies its
core concept and any central misconception has been addressed. Implementation
completion alone does not complete the lesson. Do not turn these distinctions
into status fields in the learning log. Understanding can be revisited later.

## Documentation ownership

- AGENTS.md: mandatory collaboration and documentation rules.
- PROJECT_CONTEXT.md: lasting application facts, architecture, limitations.
- LEARNING_ROADMAP.md: proposed progression and session workflow.
- LEARNING_LOG.md: brief active topic/question and substantive learning records.

Update context when implementation changes its facts, the roadmap when learning
priorities change, and AGENTS.md when collaboration rules change. Avoid copying
current progress across files.
