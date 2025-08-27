CREATE TABLE if not exists user_info (
    user_id UUID PRIMARY KEY,
    ai_token varchar(100) not null,
    model varchar(30) not null,
    current_weight numeric(5,2) not null,
    desired_weight numeric(5,2) not null,
    automatic_update boolean not null,
    train_plan_options varchar(2000) not null,
    meal_plan_options varchar(2000) not null,
    created_at TIMESTAMP NOT NULL,
    update_at TIMESTAMP NOT NULL
);