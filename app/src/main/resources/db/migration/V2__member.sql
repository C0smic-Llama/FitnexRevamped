CREATE TABLE member_db.members(
      id            uuid PRIMARY KEY,
      name          varchar(200) NOT NULL,
      email         varchar(300) NOT NULL,
      status        varchar(20) NOT NULL,
      cognito_sub   varchar(64),
      created_at    timestamptz NOT NULL,
      updated_at    timestamptz NOT NULL
);

CREATE UNIQUE INDEX uq_members_email on member_db.members(lower(email));
CREATE UNIQUE INDEX uq_cognito_sub on member_db.members(cognito_sub) WHERE cognito_sub IS NOT NULL;