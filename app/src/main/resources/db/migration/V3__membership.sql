CREATE TABLE member_db.plans
(
    id       uuid PRIMARY KEY,
    name     varchar(100) NOT NULL,
    code     varchar(50)  NOT NULL UNIQUE,
    duration int          NOT NULL CHECK ( duration > 0 ),
    price    bigint       NOT NULL CHECK ( price > 0 ),
    active   boolean      NOT NULL DEFAULT TRUE
);

CREATE TABLE member_db.known_members
(
    member_id     uuid PRIMARY KEY,
    name          varchar(50)  NOT NULL,
    email         varchar(100) NOT NULL,
    registered_at timestamptz  NOT NULL,
    received_at   timestamptz  NOT NULL
);

CREATE TABLE member_db.subscriptions
(
    id           uuid PRIMARY KEY,
    member_id    uuid        NOT NULL REFERENCES member_db.known_members (member_id),
    plan_id      uuid        NOT NULL REFERENCES member_db.plans (id),
    status       varchar(20) NOT NULL,
    amount       bigint      NOT NULL,
    invoice_id   uuid,
    payment_url  text,
    created_at   timestamptz NOT NULL,
    updated_at   timestamptz NOT NULL,
    activated_at timestamptz NOT NULL,
    version bigint NOT NULL DEFAULT 0
);