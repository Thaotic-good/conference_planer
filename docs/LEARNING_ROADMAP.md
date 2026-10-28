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
learner understanding from existing code. Keep the active exercise and exact
next action only in LEARNING_LOG.md.

### 2. Let the learner attempt it

Explain enough to make the exercise approachable, then allow the learner to
reason or implement. Prefer a focused hint before a complete solution.

An explicit implementation request authorizes Codex to implement, but does not
establish learner understanding.

### 3. Review behavior and reasoning

Review whether the implementation behaves correctly and whether the learner's
explanation matches the concept. Identify what is correct, what is mistaken,
and why. Separate conceptual misunderstandings from syntax mistakes and
framework details.

### 4. Resolve misunderstandings

When an explanation reveals a misconception:
1. Identify the specific mistaken assumption.
2. Explain it using current code or a minimal counterexample.
3. Ask the learner to apply the corrected idea to a small variation.
4. Reassess the explanation.

Keep the unresolved concept active until the learner demonstrates the core
idea. Repeating Codex's wording alone is insufficient evidence. Avoid turning
every detail into a quiz; check the concept central to the exercise.

If the learner stops or changes focus, preserve the unresolved concept and next
step. Do not mark understanding demonstrated or force continued questioning.

### 5. Verify and record

Run checks appropriate to the change. Record implementation progress separately
from understanding. At meaningful exercise boundaries and session end, update
factual progress and the exact next action, even if reflection is pending.

Ask the learner to supply:
- What I can now explain or do.
- Evidence.
- What remains unclear.

Codex may transcribe a conversational reflection or help clarify its wording,
but must not invent first-person claims or strengthen what was demonstrated.
Use "Learner reflection pending" when none was supplied. Attribute Codex's
assessment separately, with its basis and any remaining correction.

## Completion criteria

Track two separate outcomes:
- Implementation: planned, in progress, or verified.
- Understanding: not assessed, developing, or demonstrated.

Distinguish observed code from verified behavior: cite checks actually run and
label historical validation reports. Tests alone do not prove understanding.

An exercise is complete when the agreed behavior is verified and the learner
has demonstrated its primary concept through explanation or application to a
small variation. Understanding can be reassessed later; one explanation does
not establish permanent mastery.

## Documentation ownership

- AGENTS.md: mandatory collaboration and documentation rules.
- PROJECT_CONTEXT.md: application facts, architecture, limitations, validation.
- LEARNING_ROADMAP.md: proposed progression and session workflow.
- LEARNING_LOG.md: active exercise, learner reflections, assessments, next action.

Update context when implementation changes its facts, the roadmap when learning
priorities change, and AGENTS.md when collaboration rules change. Avoid copying
current progress across files.
