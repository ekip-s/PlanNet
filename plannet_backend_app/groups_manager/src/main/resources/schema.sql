CREATE TABLE if not exists groups (
    id UUID PRIMARY KEY,
    owner UUID NOT NULL,
    created_at TIMESTAMP NOT NULL,
    name VARCHAR(50) NOT NULL
);

CREATE INDEX if not exists idx_groups_owner ON groups (owner);

CREATE TABLE if not exists group_user (
    group_id UUID NOT NULL,
    user_id UUID NOT NULL,
    user_login VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    role SMALLINT NOT NULL,
    PRIMARY KEY (group_id, user_id),
    FOREIGN KEY (group_id)
        REFERENCES groups(id)
        ON DELETE CASCADE
);

CREATE INDEX if not exists idx_group_user_user_id ON group_user (user_id);

CREATE TABLE if not exists group_services (
    id UUID PRIMARY KEY,
    name SMALLINT NOT NULL,
    group_id UUID NOT NULL,
    created_at TIMESTAMP NOT NULL,
    FOREIGN KEY (group_id)
        REFERENCES groups(id)
        ON DELETE CASCADE
);

CREATE INDEX if not exists idx_group_services_group ON group_services (group_id);
CREATE INDEX if not exists idx_group_services_name ON group_services (name);

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