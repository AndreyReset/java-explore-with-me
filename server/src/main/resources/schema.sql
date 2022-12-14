CREATE TABLE IF NOT EXISTS categories (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name VARCHAR(100) NOT NULL,
    CONSTRAINT pk_categories PRIMARY KEY (id),
    CONSTRAINT uq_name UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS location (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    lat FLOAT NOT NULL,
    lon FLOAT NOT NULL,
    CONSTRAINT pk_location PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS users (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (id),
    CONSTRAINT uq_user_email UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS compilations (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    pinned BOOLEAN NOT NULL,
    title VARCHAR(120) NOT NULL,
    CONSTRAINT pk_comp PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS participation (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    created TIMESTAMP WITHOUT TIME ZONE,
    event_id BIGINT NOT NULL,
    requester_id BIGINT NOT NULL,
    status VARCHAR(50) NOT NULL,
    CONSTRAINT pk_part PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS compilation_event (
    compilation_id BIGINT NOT NULL,
    event_id BIGINT NOT NULL,
    CONSTRAINT pk_comp_ev PRIMARY KEY (compilation_id, event_id)
);

CREATE TABLE IF NOT EXISTS events (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    title VARCHAR(120) NOT NULL,
    annotation VARCHAR(2000) NOT NULL,
    description VARCHAR(7000) NOT NULL,
    status VARCHAR(50) NOT NULL,
    views INTEGER,
    confirmed_requests INTEGER NOT NULL,
    participant_limit INTEGER NOT NULL,
    paid BOOLEAN NOT NULL,
    requester_moderation BOOLEAN NOT NULL,
    event_date TIMESTAMP WITHOUT TIME ZONE,
    created_on TIMESTAMP WITHOUT TIME ZONE,
    published_on TIMESTAMP WITHOUT TIME ZONE,
    category_id BIGINT NOT NULL,
    initiator_id BIGINT NOT NULL,
    location_id BIGINT NOT NULL,
    CONSTRAINT pk_events PRIMARY KEY (id)
);

ALTER TABLE participation ADD FOREIGN KEY (event_id) REFERENCES events(id);
ALTER TABLE participation ADD FOREIGN KEY (requester_id) REFERENCES users(id);
ALTER TABLE compilation_event ADD FOREIGN KEY (compilation_id) REFERENCES compilations(id);
ALTER TABLE compilation_event ADD FOREIGN KEY (event_id) REFERENCES events(id);
ALTER TABLE events ADD FOREIGN KEY (category_id) REFERENCES categories(id);
ALTER TABLE events ADD FOREIGN KEY (initiator_id) REFERENCES users(id);
ALTER TABLE events ADD FOREIGN KEY (location_id) REFERENCES location(id);