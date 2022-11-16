CREATE TABLE IF NOT EXISTS users
(
    user_id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name    VARCHAR(255)                            NOT NULL,
    email   VARCHAR(512)                            NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (user_id),
    CONSTRAINT UQ_USER_EMAIL UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS categories
(
    category_id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name        VARCHAR(255)                            NOT NULL,
    CONSTRAINT pk_category PRIMARY KEY (category_id),
    CONSTRAINT UQ_CAT_NAME UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS compilations
(
    compilation_id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    pinned         BOOLEAN,
    title          VARCHAR(255),
    CONSTRAINT pk_compilation PRIMARY KEY (compilation_id)
);

CREATE TABLE IF NOT EXISTS events
(
    event_id           BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    annotation         VARCHAR(2000)                           NOT NULL,
    category_id        BIGINT                                  NOT NULL,
    confirmed_requests INT                                     NOT NULL,
    created_on         TIMESTAMP WITHOUT TIME ZONE             NOT NULL,
    description        VARCHAR(7000)                           NOT NULL,
    event_date         TIMESTAMP WITHOUT TIME ZONE             NOT NULL,
    initiator_id       BIGINT                                  NOT NULL,
    lat                FLOAT                                   NOT NULL,
    lon                FLOAT                                   NOT NULL,
    paid               BOOLEAN,
    participant_limit  INT,
    published_on       TIMESTAMP WITHOUT TIME ZONE             NOT NULL,
    request_moderation BOOLEAN,
    state              VARCHAR(100)                            NOT NULL,
    title              VARCHAR(120)                            NOT NULL,
    views              INT,
    CONSTRAINT pk_event PRIMARY KEY (event_id),
    CONSTRAINT FK_EVENT_ON_CATEGORY FOREIGN KEY (category_id) REFERENCES categories (category_id) ON DELETE RESTRICT,
    CONSTRAINT FK_EVENT_ON_INITIATOR FOREIGN KEY (initiator_id) REFERENCES users (user_id) ON DELETE CASCADE,
);

CREATE TABLE IF NOT EXISTS events_compilations
(
    event_id       BIGINT NOT NULL,
    compilation_id BIGINT NOT NULL,
    CONSTRAINT pk_events_comp PRIMARY KEY (event_id, compilation_id),
    CONSTRAINT FK_EVENT_EVENT FOREIGN KEY (event_id) REFERENCES events (event_id) ON DELETE CASCADE,
    CONSTRAINT FK_COMP_COMP FOREIGN KEY (compilation_id) REFERENCES compilations (compilation_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS participation_requests
(
    request_id   BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    crated_on    TIMESTAMP WITHOUT TIME ZONE             NOT NULL,
    event_id     BIGINT                                  NOT NULL,
    requester_id BIGINT                                  NOT NULL,
    status       varchar(100)                            NOT NULL,
    CONSTRAINT pk_request PRIMARY KEY (request_id),
    CONSTRAINT FK_REQUEST_EVENT FOREIGN KEY (request_id) REFERENCES events (event_id) ON DELETE CASCADE,
    CONSTRAINT FK_REQUEST_REQUESTER FOREIGN KEY (requester_id) REFERENCES users (user_id) ON DELETE CASCADE
);