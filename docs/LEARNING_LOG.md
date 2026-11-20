# Learning Log

Keep this file brief. Record learning conversations, not implementation history
or agent activity. See [LEARNING_ROADMAP.md](LEARNING_ROADMAP.md) for the workflow.

## Active exercise

**Topic:** JPA relationship ownership and schema alignment.

**Goal:** Explain how the session-to-event relationship is represented in both
JPA and PostgreSQL.

**Pending question:** If `EventEntity` later adds
`@OneToMany(mappedBy = "event")`, which side should be changed when moving a
session to another event, and which database column changes?

## Learning records

Add a dated record only when the learner has supplied a meaningful reflection
or explanation. Conversational answers can be transcribed without strengthening
their claims. Do not add empty records, implementation summaries, or test results.

```text
YYYY-MM-DD — Topic
What I can now explain or do:
Evidence (exercise, test, or example):
What remains unclear:
```

Evidence describes the learner's reasoning or application. If useful, append a brief, separately attributed Codex assessment with
a correction or evidence of understanding. Keep unresolved misconceptions in
the active exercise until discussed and reassessed.

2026-11-05 — Composition testing
What I can now explain or do: A test that boots more infrastructure can reveal
whether the application layers work together.
Evidence (exercise, test, or example): Distinguished the proposed endpoint-to-
database test from tests of individual layers.
What remains unclear: Which concrete defects are visible only when the real
layers are composed.

2026-11-06 — Layered validation
What I can now explain or do: The invalid time range is protected by service
validation and a database constraint.
Evidence (exercise, test, or example): Predicted that equal start and end times
would be rejected instead of being inserted.
What remains unclear: The endpoint integration test invokes the real service;
the database constraint is reached only if earlier validation is bypassed or
defective.

2026-11-20 — JPA relationship ownership
What I can now explain or do: A program session belongs to one event, so the
many-side `program_sessions` row stores `event_id`; `ProgramSessionEntity` owns
the JPA relationship.
Evidence (exercise, test, or example): Explained how `event_id` distinguishes
the event to which each session belongs and connected that foreign key to JPA
ownership.
What remains unclear: Reverse navigation from an event does not move or
duplicate the foreign key; a future event collection would be the inverse view
of the same relationship.
