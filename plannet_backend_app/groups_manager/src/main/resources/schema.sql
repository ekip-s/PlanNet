CREATE TABLE if not exists groups (
    id UUID PRIMARY KEY,
    created_at TIMESTAMP NOT NULL,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE if not exists service (
    id UUID PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    created_at TIMESTAMP NOT NULL
);

CREATE TABLE if not exists group_services (
    group_id UUID NOT NULL,
    service_id UUID NOT NULL,
    created_at TIMESTAMP NOT NULL,
    PRIMARY KEY (group_id, service_id),
    FOREIGN KEY (group_id) REFERENCES groups(id) ON DELETE CASCADE,
    FOREIGN KEY (service_id) REFERENCES service(id) ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS idx_group_services_group ON group_services (group_id);
CREATE INDEX IF NOT EXISTS idx_group_services_service ON group_services (service_id);

CREATE TABLE if not exists group_user (
    group_id UUID NOT NULL,
    user_id UUID NOT NULL,
    user_login VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    role SMALLINT NOT NULL,
    PRIMARY KEY (group_id, user_id),
    FOREIGN KEY (group_id) REFERENCES groups(id) ON DELETE CASCADE
);

CREATE INDEX if not exists idx_group_user_user_id ON group_user (user_id);

CREATE TABLE if not exists invitations (
    id UUID PRIMARY KEY,
    code VARCHAR(50) NOT NULL UNIQUE,
    created_at TIMESTAMP NOT NULL,
    group_id UUID NOT NULL,
    FOREIGN KEY (group_id)
        REFERENCES groups(id)
        ON DELETE CASCADE
);

CREATE INDEX if not exists idx_invitations_group ON invitations (group_id);

INSERT INTO service (id, name, created_at) VALUES
    (gen_random_uuid(), 'tasks', current_timestamp)
 ON CONFLICT DO NOTHING;