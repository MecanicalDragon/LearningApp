CREATE SEQUENCE IF NOT EXISTS CUSTOMER_SEQUENCE START WITH 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS CUSTOMER
(
    ID      BIGINT PRIMARY KEY NOT NULL DEFAULT NEXTVAL('CUSTOMER_SEQUENCE'),
    NAME    VARCHAR(255)       NOT NULL,
    CITY    VARCHAR(255)       NOT NULL,
    COUNTRY VARCHAR(255)       NOT NULL
);

CREATE SEQUENCE IF NOT EXISTS ORDER_SEQUENCE START WITH 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS PARCEL
(
    ID          BIGINT PRIMARY KEY NOT NULL DEFAULT NEXTVAL('ORDER_SEQUENCE'),
    NAME        VARCHAR(255)       NOT NULL,
    DEPARTURE   VARCHAR(255)       NOT NULL,
    DESTINATION VARCHAR(255)       NOT NULL,
    OWNER       BIGINT REFERENCES CUSTOMER (ID)
);

CREATE INDEX IF NOT EXISTS ORDER_OWNER ON PARCEL (OWNER)