CREATE TABLE events (
    id uuid PRIMARY KEY,
    name TEXT NOT NULL UNIQUE,
    country_code CHAR(2) NOT NULL,
    time_zone_id VARCHAR(64) NOT NULL,
    -- service gives a friendly error, the database constraint is final safety net
    starts_at TIMESTAMPTZ NOT NULL,
    ends_at TIMESTAMPTZ NOT NULL,
    event_status TEXT NOT NULL,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL,

    CONSTRAINT check_events_time_range
        CHECK ( ends_at > starts_at ),

    CONSTRAINT check_events_status
        CHECK ( event_status IN ('DRAFT', 'CONFIRMED', 'CLOSED') ),

    CONSTRAINT check_events_country_code
        CHECK ( country_code ~ '^[A-Z]{2}$' ),

    CONSTRAINT check_events_time_zone_id
        CHECK ( time_zone_id <> '' )
);
