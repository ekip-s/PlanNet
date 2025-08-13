CREATE TABLE if not exists target (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    parent_id UUID NULL,
    type VARCHAR(20) NOT NULL,
    status VARCHAR(20) NOT NULL,
    title varchar(255) NOT NULL,
    description TEXT,
    created_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_parent FOREIGN KEY (parent_id) REFERENCES target(id) ON DELETE CASCADE
);

CREATE INDEX if not exists idx_target_user_type ON target (user_id, type);
CREATE INDEX if not exists idx_target_parent_user ON target (parent_id, user_id);
CREATE INDEX if not exists idx_target_user ON target (user_id);