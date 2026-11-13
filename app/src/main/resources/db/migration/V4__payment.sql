CREATE TABLE member_db.invoices
(
    id              uuid PRIMARY KEY,
    subscription_id uuid        NOT NULL,
    member_id       uuid        NOT NULL,
    amount          bigint      NOT NULL,
    status          varchar(20) NOT NULL,
    payment_url     text        NOT NULL,
    created_at      timestamptz NOT NULL,
    paid_at         timestamptz,
    version         bigint      NOT NULL DEFAULT 0

);

CREATE TABLE member_db.payments
(
    id                  uuid PRIMARY KEY,
    invoice_id          uuid         NOT NULL REFERENCES member_db.invoices (id),
    provider_payment_id varchar(100) NOT NULL UNIQUE,
    amount              bigint       NOT NULL,
    status              varchar(20)  NOT NULL,
    paid_at             timestamptz  NOT NULL
);