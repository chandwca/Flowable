--
-- PostgreSQL database dump
--

CREATE SCHEMA IF NOT EXISTS app AUTHORIZATION postgres;

DROP TABLE IF EXISTS app.users;

CREATE TABLE IF NOT EXISTS app.users
(
    created_at timestamp(6) without time zone,
    updated_at timestamp(6) without time zone,
    id uuid NOT NULL,
    name character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT users_pkey PRIMARY KEY (id)
);

INSERT INTO app.users(created_at, updated_at, id, name)
VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '067053f8-eb65-11ef-af17-f3da1e3f18ca', 'Test');

-- TABLESPACE pg_default;
