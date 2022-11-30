--liquibase formatted sql

--changeset sandis:2


CREATE TABLE flight
(
    id             serial PRIMARY KEY,
    airport_from   TEXT      NOT NULL,
    airport_to     TEXT      NOT NULL,
    carrier        text      NOT NULL,
    departure_time timestamp NOT NULL,
    arrival_time   timestamp NOT NULL,
    CONSTRAINT fk_airport_from FOREIGN KEY (airport_from) REFERENCES airport (airport),
    CONSTRAINT fk_airport_to FOREIGN KEY (airport_to) REFERENCES airport (airport)

)

