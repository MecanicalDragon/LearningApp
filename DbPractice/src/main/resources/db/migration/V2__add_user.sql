CREATE SEQUENCE IF NOT EXISTS USER_SEQUENCE START WITH 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS "USER"
(
    ID      BIGINT PRIMARY KEY NOT NULL,
    NAME    VARCHAR(255)       NOT NULL,
    SURNAME VARCHAR(255)       NOT NULL,
    AGE     INT                NOT NULL
);