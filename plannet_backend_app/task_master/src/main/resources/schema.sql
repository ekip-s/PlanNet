CREATE TABLE if not exists tasks (
  id UUID PRIMARY KEY,
  title VARCHAR(255) NOT NULL,
  description TEXT,
  creator_id UUID NOT NULL,
  assignee_id UUID NOT NULL,
  group_id UUID NULL,
  parent_id UUID NULL REFERENCES tasks(id),
  status VARCHAR(50) NOT NULL DEFAULT 'new',
  priority smallint DEFAULT 1,
  deadline TIMESTAMP NULL,
  created_at TIMESTAMP NOT NULL DEFAULT NOW(),
  updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_tasks_group_status ON tasks(group_id, status);
CREATE INDEX idx_tasks_assignee_status ON tasks(assignee_id, status);