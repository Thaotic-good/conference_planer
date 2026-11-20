CREATE TABLE program_sessions (
    id UUID PRIMARY KEY,
    session_format TEXT NOT NULL,
    title TEXT NOT NULL,
    description TEXT,
    minimal_required_capacity INT NOT NULL,
    event_id UUID NOT NULL,
    starts_at TIMESTAMPTZ NOT NULL,
    ends_at TIMESTAMPTZ NOT NULL,
    setup_minutes INT,
    teardown_minutes INT,
    status TEXT NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL,
    created_at TIMESTAMPTZ NOT NULL,

    CONSTRAINT fk_program_sessions_event
        FOREIGN KEY (event_id)
        REFERENCES events (id)
        ON DELETE CASCADE,

    CONSTRAINT check_program_sessions_time_range
        CHECK ( ends_at > starts_at ),

    CONSTRAINT check_program_sessions_format
        CHECK (
            session_format IN (
                'PANEL_DISCUSSION',
                'PRESENTATION',
                'WORKSHOP',
                'CULTURE_PROGRAMME',
                'CEREMONY',
                'BREAK'
            )
        ),

    CONSTRAINT check_program_sessions_minimal_capacity
        CHECK ( minimal_required_capacity > 0 ),

    CONSTRAINT check_program_sessions_setup_minutes
        CHECK ( setup_minutes IS NULL OR setup_minutes >= 0 ),

    CONSTRAINT check_program_sessions_teardown_minutes
        CHECK ( teardown_minutes IS NULL OR teardown_minutes >= 0 ),

    CONSTRAINT check_program_sessions_status
        CHECK ( status IN ('DRAFT', 'CONFIRMED', 'DONE', 'CANCELED') )
);

CREATE INDEX idx_program_sessions_event_id
    ON program_sessions (event_id);

CREATE TABLE session_required_equipment (
    session_id UUID NOT NULL,
    equipment_type VARCHAR(50) NOT NULL,
    quantity INT NOT NULL,

    -- session_id links to the parent session; pairing it with equipment_type
    -- allows many equipment types per session but only one row per type.
    CONSTRAINT pk_session_required_equipment
        PRIMARY KEY (session_id, equipment_type),

    CONSTRAINT fk_session_required_equipment
        FOREIGN KEY (session_id)
        REFERENCES program_sessions (id)
        ON DELETE CASCADE,

    CONSTRAINT chk_session_equipment_quantity
        CHECK ( quantity > 0 ),

    CONSTRAINT chk_session_equipment_type
        CHECK (
            equipment_type IN (
                'PROJECTOR',
                'MICROPHONE',
                'LIVESTREAMING',
                'WORKSHOP_TABLES',
                'WHEELCHAIR_ACCESS'
            )
        )
);
