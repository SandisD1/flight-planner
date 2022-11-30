--liquibase formatted sql

--changeset sandis:1

CREATE TABLE airport
(
    country text NOT NULL,
    city    text NOT NULL,
    airport text PRIMARY KEY
)