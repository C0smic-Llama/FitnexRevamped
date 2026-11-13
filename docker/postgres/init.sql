CREATE USER member_svc WITH PASSWORD 'member_svc';
CREATE DATABASE member_db OWNER member_svc;

CREATE USER membership_svc WITH PASSWORD 'membership_svc';
CREATE DATABASE membership_db OWNER membership_svc;

CREATE USER payment_svc WITH PASSWORD 'payment_svc';
CREATE DATABASE payment_db OWNER payment_svc;